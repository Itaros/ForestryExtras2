package forestryextras.items.frames;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import org.lwjgl.input.Keyboard;

import thaumcraftextras.api.energy.IToolMCKE;
import thaumcraftextras.api.functions.ChargerFunctions;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.apiculture.IHiveFrame;
import forestry.api.recipes.RecipeManagers;
import forestryextras.main.Main;
import forestryextras.main.init.Tabs;

public final class FEItemFrameMCKE extends Item implements IHiveFrame, IToolMCKE{

    public FEItemFrameMCKE(int id, int energyAmount, boolean isHelish, boolean isSimulated, boolean isSelfLighted, boolean isSealed,
    		float frameDecay, float floweringMod, float productionMod, float lifespanMod,
    		float mutationMod, float territoryMod, String itemName, String oreDictName, String textureName, int frameColor,
    		ItemStack bindingMaterial, ItemStack frameMaterial, boolean easyRecipe, FluidStack recipeFluid, int creationTime)
    {
        super(id);
		setUnlocalizedName(Main.alias.toLowerCase() + "." + "frame" + "." + "frameMCKE");
        setCreativeTab(Tabs.tabMain);
        setMaxStackSize(1);
        setMaxDamage(energyAmount);
        
        helish = isHelish;
        simulated = isSimulated;
        selflighted = isSelfLighted;
        sealed = isSealed;
        decay = frameDecay;
        flowering = floweringMod;
        productionmodifier = productionMod;
        lifespanmodifier = lifespanMod;
        mutationmodifier = mutationMod;
        territorymodifier = territoryMod;        
      
        name = itemName;
        oreDict = oreDictName;
        texture = textureName;
        color = frameColor;
        uses = energyAmount;

        frameMat = frameMaterial;
        bindingMat = bindingMaterial;
        easyRec = easyRecipe;
        recFluid = recipeFluid;
        createTime = creationTime;
        init();
    }
    boolean helish;
    boolean simulated;
    boolean selflighted;
    boolean sealed;
    float decay;
    float flowering;
    float productionmodifier;
    float lifespanmodifier;
    float mutationmodifier;
    float territorymodifier;
	String name;
	String oreDict;
	int color;
    String texture;
    ItemStack bindingMat;
    ItemStack frameMat;
    boolean easyRec;
    FluidStack recFluid;
    int createTime;
    int uses;

    public void init()
    {
    	recipe(easyRec);
		GameRegistry.registerItem(this, this.getUnlocalizedName());
		OreDictionary.registerOre(oreDict, this); 
		ChargerFunctions.addChargeAble(this);
//		ListHelper.list.put(ListHelper.list.size() + 1, this.getItemDisplayName(new ItemStack(this)));
	}
    
    public void recipe(boolean easy)
    {
    	if(easy == true){
    		GameRegistry.addShapedRecipe(new ItemStack(this, 1, getMaxDamage()), new Object[]{
    			"XXX",
    			"XIX",
    			"XXX",
    			'X', frameMat,
    			'I', bindingMat});
    	}else{
    		RecipeManagers.carpenterManager.addRecipe(createTime, recFluid, null, new ItemStack(this, 1, getMaxDamage()), new Object[]{
    			"XXX",
    			"XIX",
    			"XXX",
    			'X', frameMat,
    			'I', bindingMat});
    	}
    }
    
	@Override
	public float getTerritoryModifier(IBeeGenome genome, float currentModifier) {
		return territorymodifier;
	}

	@Override
	public float getMutationModifier(IBeeGenome genome, IBeeGenome mate,
			float currentModifier) {
		return mutationmodifier;
	}

	@Override
	public float getLifespanModifier(IBeeGenome genome, IBeeGenome mate,
			float currentModifier) {
		return lifespanmodifier;
	}

	@Override
	public float getProductionModifier(IBeeGenome genome, float currentModifier) {
		return productionmodifier;
	}

	@Override
	public float getFloweringModifier(IBeeGenome genome, float currentModifier) {
		return flowering;
	}

	@Override
	public float getGeneticDecay(IBeeGenome genome, float currentModifier) {
		return decay;
	}

	@Override
	public boolean isSealed() {
		return sealed;
	}

	@Override
	public boolean isSelfLighted() {
		return selflighted;
	}

	@Override
	public boolean isSunlightSimulated() {
		return simulated;
	}

	@Override
	public boolean isHellish() {
		return helish;
	}

	@Override
	public ItemStack frameUsed(IBeeHousing housing, ItemStack frame,
			IBee queen, int wear) {
		 frame.setItemDamage(frame.getItemDamage() + wear);
	        if(frame.getItemDamage() >= frame.getMaxDamage())
	            return null;
	        else
	            return frame;
	}
	
	@Override
    public void registerIcons(IconRegister ir) 
	{
        itemIcon = ir.registerIcon(Main.modName.toLowerCase() + ":" + texture);
	}
	
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int id)
	{
		return color;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean id)
	{	
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)){
			list.add(EnumChatFormatting.RED + "Durability: " + EnumChatFormatting.GRAY + uses);
			list.add(EnumChatFormatting.RED + "isHelish: " + EnumChatFormatting.GRAY + helish);
			list.add(EnumChatFormatting.RED + "isSimulated: " + EnumChatFormatting.GRAY + simulated);
			list.add(EnumChatFormatting.RED + "isSelflighted: " + EnumChatFormatting.GRAY +  selflighted);
			list.add(EnumChatFormatting.RED + "isSealed: " + EnumChatFormatting.GRAY + sealed);
			list.add(EnumChatFormatting.RED + "Decay: " + EnumChatFormatting.GRAY +  decay * 100 + "%");
			list.add(EnumChatFormatting.RED + "Flowering: " + EnumChatFormatting.GRAY + flowering*100 + "%");
			list.add(EnumChatFormatting.RED + "Production Modifier: " + EnumChatFormatting.GRAY +  productionmodifier*100 + "%");
			list.add(EnumChatFormatting.RED + "Lifespan Modifier: " + EnumChatFormatting.GRAY +  lifespanmodifier*100 + "%");
			list.add(EnumChatFormatting.RED + "Mutation Modifier: " + EnumChatFormatting.GRAY + mutationmodifier*100 + "%");
			list.add(EnumChatFormatting.RED + "Territory Modifier: " + EnumChatFormatting.GRAY + territorymodifier*100 + "%");
		}else{
			list.add(EnumChatFormatting.GREEN + "Press " + "Shift " + "for more info.");
		}
	}

	@Override
	public int getAmountOfEnergy(ItemStack stack) {
		return getDamage(stack);
	}

	@Override
	public void getCostForMode(ItemStack stack, int cost) {
	}

	@Override
	public void setAmountOfEnergy(ItemStack stack, int amount) {
		stack.setItemDamage(amount);
	}

	@Override
	public void setCostForMode(ItemStack arg0, int arg1, int arg2) {
		
	}
}