package net.lumerite.lumeritemod.core.particles;

import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class CustomDustParticles {

    public static final Vector3f URANIUM_COLOR_PARTICLE = Vec3.fromRGB24(6025573).toVector3f();
    public static final DustParticleOptions URANIUM = new DustParticleOptions(URANIUM_COLOR_PARTICLE, 1.0F);
}
