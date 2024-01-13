package io.github.mattidragon.gadgets_of_the_sky.block;

import io.github.mattidragon.gadgets_of_the_sky.recipe.ModRecipes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class SkyAltarBlockEntity extends LootableContainerBlockEntity {
    private static final DefaultedList<ItemStack> EMPTY_INVENTORY = DefaultedList.ofSize(0, ItemStack.EMPTY);
    public static final int MAX_CRAFTING_TIME = 100;
    private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
    private ItemStack craftingStack = null;
    private int craftingTime = 0;
    private boolean active = false;

    protected SkyAltarBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlocks.SKY_ALTAR_BLOCK_ENTITY, blockPos, blockState);
    }

    public void tick() {
        if (this.world == null) return;
        if (world.isClient) {
            if (craftingTime == 1) SkyAltarBlock.spawnCraftingFlames(world, pos);
            return;
        }

        generateLoot(null);

        if (this.craftingTime > 0) {
            --this.craftingTime;
            if (this.craftingTime == 0) {
                this.finishCrafting();
            }
            markDirty();
        } else if (canCraft(inventory.get(0))) {
            this.beginCrafting();
            markDirty();
        }
    }

    private boolean canCraft(ItemStack stack) {
        if (world == null) return false;
        if (!active) return stack.isOf(Items.HEART_OF_THE_SEA);
        if (!hasCharge()) return stack.isOf(Items.GLOWSTONE_DUST);
        return world.getRecipeManager().getFirstMatch(ModRecipes.SKY_ALTAR_TYPE, new SimpleInventory(stack), world).isPresent();
    }

    private void beginCrafting() {
        craftingTime = MAX_CRAFTING_TIME;
        craftingStack = inventory.get(0);
        inventory = EMPTY_INVENTORY;
    }

    private void finishCrafting() {
        inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
        if (!active) {
            active = true;
            playCraftSound(1, 1);
        } else if (!hasCharge()) {
            fillCharges();
            playCraftSound(1, 1);
        } else {
            inventory.set(0, craft(craftingStack));
            consumeCharge();
            playCraftSound(0.5f, 1.5f);
        }
        craftingStack = null;
    }

    private void playCraftSound(float volume, float pitch) {
        if (world != null && !world.isClient) {
            world.playSound(null, pos, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, volume, pitch);
        }
    }

    public int getCraftingTime() {
        return craftingTime;
    }

    public boolean isCrafting() {
        return craftingTime > 0;
    }

    public boolean isActive() {
        return active;
    }

    public boolean hasCharge() {
        return getCachedState().get(SkyAltarBlock.CHARGES) > 0;
    }

    public ItemStack getDisplayStack() {
        if (craftingStack != null) return craftingStack;
        if (!inventory.isEmpty()) return inventory.get(0);
        return ItemStack.EMPTY;
    }

    private void consumeCharge() {
        if (world == null) return;
        var state = getCachedState();
        var charges = state.get(SkyAltarBlock.CHARGES);
        if (charges > 0) {
            world.setBlockState(pos, state.with(SkyAltarBlock.CHARGES, charges - 1));
        }
    }

    private void fillCharges() {
        if (world == null) return;
        var state = getCachedState();
        var charges = state.get(SkyAltarBlock.CHARGES);
        if (charges < 3) {
            world.setBlockState(pos, state.with(SkyAltarBlock.CHARGES, 3));
        }
    }

    public ItemStack craft(ItemStack input) {
        if (world == null) return ItemStack.EMPTY;
        var craftingInventory = new SimpleInventory(input);
        return world.getRecipeManager()
                .getFirstMatch(ModRecipes.SKY_ALTAR_TYPE, craftingInventory, world)
                .map(RecipeEntry::value)
                .map(recipe -> recipe.craft(craftingInventory, world.getRegistryManager()))
                .orElse(ItemStack.EMPTY);
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        var nbt = new NbtCompound();
        writeNbt(nbt);
        return nbt;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.craftingTime = nbt.getInt("CraftingTime");
        this.craftingStack = ItemStack.fromNbt(nbt.getCompound("CraftingStack"));
        if (craftingStack.isEmpty()) craftingStack = null;
        this.inventory = DefaultedList.ofSize(craftingTime > 0 ? 0 : 1, ItemStack.EMPTY);
        if (!this.readLootTable(nbt)) {
            Inventories.readNbt(nbt, this.inventory);
        }
        this.active = nbt.getBoolean("Active");
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("CraftingTime", craftingTime);
        nbt.put("CraftingStack", (craftingStack == null ? ItemStack.EMPTY : craftingStack).writeNbt(new NbtCompound()));
        if (!this.writeLootTable(nbt)) {
            Inventories.writeNbt(nbt, this.inventory);
        }
        nbt.putBoolean("Active", active);
    }

    @Override
    protected DefaultedList<ItemStack> method_11282() {
        // If we are about to begin crafting we don't show the item
        // It will be removed the next tick anyway
        if (!isCrafting() && canCraft(inventory.get(0))) return EMPTY_INVENTORY;
        return inventory;
    }

    @Override
    protected void setInvStackList(DefaultedList<ItemStack> list) {
        inventory = list;
    }

    @Override
    public int getMaxCountPerStack() {
        return 1;
    }

    @Override
    public boolean isValid(int slot, ItemStack stack) {
        if (world != null) {
            return canCraft(stack);
        } else {
            return false;
        }
    }

    @Override
    protected Text getContainerName() {
        return ModBlocks.SKY_ALTAR.getName();
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return null;
    }

    @Override
    public int size() {
        return method_11282().size();
    }

    @Override
    public void markDirty() {
        super.markDirty();
        if (world != null) {
            world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);
        }
    }

    public Inventory getDropInventory() {
        var inventory = new SimpleInventory(3);
        if (active) inventory.setStack(0, new ItemStack(Items.HEART_OF_THE_SEA));
        if (craftingStack != null) inventory.setStack(1, craftingStack);
        if (!this.inventory.isEmpty()) inventory.setStack(2, this.inventory.get(0));
        return inventory;
    }
}
