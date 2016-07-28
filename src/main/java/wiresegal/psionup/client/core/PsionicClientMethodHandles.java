package wiresegal.psionup.client.core;

import com.google.common.base.Throwables;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.apache.logging.log4j.Level;
import vazkii.psi.client.gui.GuiProgrammer;
import vazkii.psi.common.spell.SpellCompiler;
import wiresegal.psionup.common.PsionicUpgrades;
import wiresegal.psionup.common.lib.LibObfuscation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.Field;

import static java.lang.invoke.MethodHandles.publicLookup;

/**
 * @author WireSegal
 *         Created at 10:26 PM on 7/9/16.
 */
public class PsionicClientMethodHandles {

    @Nonnull
    private static final MethodHandle spellNameFieldGetter, compilerGetter, compilerSetter,
            prevEquippedProgressMainGetter, prevEquippedProgressOffGetter,
            equippedProgressMainGetter, equippedProgressOffGetter,
            stackMainGetter, stackOffGetter;

    static {
        try {
            Field f = ReflectionHelper.findField(GuiProgrammer.class, "spellNameField");
            spellNameFieldGetter = publicLookup().unreflectGetter(f);

            f = ReflectionHelper.findField(GuiProgrammer.class, "compiler");
            compilerGetter = publicLookup().unreflectGetter(f);
            compilerSetter = publicLookup().unreflectSetter(f);

            f = ReflectionHelper.findField(ItemRenderer.class, LibObfuscation.ITEMRENDERER_PREVEQUIPPEDPROGRESSMAINHAND);
            prevEquippedProgressMainGetter = publicLookup().unreflectGetter(f);

            f = ReflectionHelper.findField(ItemRenderer.class, LibObfuscation.ITEMRENDERER_PREVEQUIPPEDPROGRESSOFFHAND);
            prevEquippedProgressOffGetter = publicLookup().unreflectGetter(f);

            f = ReflectionHelper.findField(ItemRenderer.class, LibObfuscation.ITEMRENDERER_EQUIPPEDPROGRESSMAINHAND);
            equippedProgressMainGetter = publicLookup().unreflectGetter(f);

            f = ReflectionHelper.findField(ItemRenderer.class, LibObfuscation.ITEMRENDERER_EQUIPPEDPROGRESSOFFHAND);
            equippedProgressOffGetter = publicLookup().unreflectGetter(f);

            f = ReflectionHelper.findField(ItemRenderer.class, LibObfuscation.ITEMRENDERER_ITEMSTACKMAINHAND);
            stackMainGetter = publicLookup().unreflectGetter(f);

            f = ReflectionHelper.findField(ItemRenderer.class, LibObfuscation.ITEMRENDERER_ITEMSTACKOFFHAND);
            stackOffGetter = publicLookup().unreflectGetter(f);

        } catch (Throwable t) {
            PsionicUpgrades.Companion.getLOGGER().log(Level.ERROR, "Couldn't initialize methodhandles! Things will be broken!");
            t.printStackTrace();
            throw Throwables.propagate(t);
        }
    }

    @Nonnull
    public static GuiTextField getSpellNameField(@Nonnull GuiProgrammer programmer) {
        try {
            return (GuiTextField) spellNameFieldGetter.invokeExact(programmer);
        } catch (Throwable t) {
            throw propagate(t);
        }
    }

    @Nonnull
    public static SpellCompiler getSpellCompiler(@Nonnull GuiProgrammer programmer) {
        try {
            return (SpellCompiler) compilerGetter.invokeExact(programmer);
        } catch (Throwable t) {
            throw propagate(t);
        }
    }

    public static float getPrevEquipMainHand(@Nonnull ItemRenderer renderer) {
        try {
            return (float) prevEquippedProgressMainGetter.invokeExact(renderer);
        } catch (Throwable t) {
            throw propagate(t);
        }
    }

    public static float getPrevEquipOffHand(@Nonnull ItemRenderer renderer) {
        try {
            return (float) prevEquippedProgressOffGetter.invokeExact(renderer);
        } catch (Throwable t) {
            throw propagate(t);
        }
    }

    public static float getEquipMainHand(@Nonnull ItemRenderer renderer) {
        try {
            return (float) equippedProgressMainGetter.invokeExact(renderer);
        } catch (Throwable t) {
            throw propagate(t);
        }
    }

    public static float getEquipOffHand(@Nonnull ItemRenderer renderer) {
        try {
            return (float) equippedProgressOffGetter.invokeExact(renderer);
        } catch (Throwable t) {
            throw propagate(t);
        }
    }

    @Nullable
    public static ItemStack getStackMainHand(@Nonnull ItemRenderer renderer) {
        try {
            return (ItemStack) stackMainGetter.invokeExact(renderer);
        } catch (Throwable t) {
            throw propagate(t);
        }
    }

    @Nullable
    public static ItemStack getStackOffHand(@Nonnull ItemRenderer renderer) {
        try {
            return (ItemStack) stackOffGetter.invokeExact(renderer);
        } catch (Throwable t) {
            throw propagate(t);
        }
    }


    public static void setSpellCompiler(@Nonnull GuiProgrammer programmer, @Nonnull SpellCompiler compiler) {
        try {
            compilerSetter.invokeExact(programmer, compiler);
        } catch (Throwable t) {
            throw propagate(t);
        }
    }

    private static RuntimeException propagate(Throwable t) {
        PsionicUpgrades.Companion.getLOGGER().log(Level.ERROR, "Methodhandle failed!");
        t.printStackTrace();
        return Throwables.propagate(t);
    }
}
