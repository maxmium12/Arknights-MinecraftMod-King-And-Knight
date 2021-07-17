package com.nmmoc7.king_and_knight.block;

import com.nmmoc7.king_and_knight.block.base.ModBlockBase;
import com.nmmoc7.king_and_knight.block.machine.InfrastructureMachineBase;
import com.nmmoc7.king_and_knight.itemgroup.ModItemGroups;
import com.nmmoc7.king_and_knight.newgui.WeaponUpgraderBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import java.util.ArrayList;

/**
 * @author DustW
 */
public class ModBlocks {
    public static final ArrayList<Block> BLOCK_LIST = new ArrayList<>();

    public static final ModBlockBase SOURCE_ROCKS_BLOCK = new ModBlockBase("source_rocks_block", Material.IRON, ModItemGroups.BLOCK_GROUP);
    public static final ModBlockBase BUILDING_MATERIAL = new ModBlockBase("building_material", Material.IRON, ModItemGroups.BLOCK_GROUP);

    public static final ModBlockBase INFRASTRUCTURE_ONE = new InfrastructureMachineBase(1);
    public static final ModBlockBase INFRASTRUCTURE_TWO = new InfrastructureMachineBase(2);
    public static final ModBlockBase INFRASTRUCTURE_THREE = new InfrastructureMachineBase(3);

    public static final ModBlockBase WEAPON_UPGRADER = new WeaponUpgraderBlock();

}
