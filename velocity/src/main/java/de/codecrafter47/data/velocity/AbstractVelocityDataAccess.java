package de.codecrafter47.data.velocity;

import com.velocitypowered.api.plugin.Plugin;
import de.codecrafter47.data.api.AbstractDataAccess;
import de.codecrafter47.data.api.DataKey;

import javax.annotation.Nullable;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractVelocityDataAccess<B> extends AbstractDataAccess<B> {
    
    protected final Plugin plugin;
    private final Logger logger;
    
    public AbstractVelocityDataAccess(Plugin plugin, Logger logger) {
        this.plugin = plugin;
        this.logger = logger;
    }
    
    @Nullable
    @Override
    public <V> V get(DataKey<V> key, B context) {
        try{
            return super.get(key, context);
        } catch (Throwable th) {
            logger.log(Level.WARNING, "Failed to aquire data " + key + " from " + context, th);
        }
        return null;
    }
}
