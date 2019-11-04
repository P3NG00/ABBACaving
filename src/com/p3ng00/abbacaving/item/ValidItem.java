package com.p3ng00.abbacaving.item;

import org.bukkit.Material;

public class ValidItem
{
	public final Material[] materials;
	public final String name;
	public final int multiplier;

	public ValidItem(String name, int multiplier, Material... items)
	{
		this.materials = items;
		this.name = name;
		this.multiplier = multiplier;
	}
}
