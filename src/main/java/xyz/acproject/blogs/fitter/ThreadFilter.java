package xyz.acproject.blogs.fitter;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.filter.AbstractFilter;
import org.apache.logging.log4j.message.Message;

@Plugin(name = "ThreadFilter", category = Node.CATEGORY, elementType = Filter.ELEMENT_TYPE, printObject = true)
public class ThreadFilter extends AbstractFilter {

    private final String threadName;

    private ThreadFilter(String threadName, Result onMatch, Result onMismatch) {
        super(onMatch, onMismatch);
        this.threadName = threadName;
    }

    public Result filter(Logger logger, Level level, Marker marker, String msg, Object[] params) {
        return filter(Thread.currentThread());
    }

    public Result filter(Logger logger, Level level, Marker marker, Object msg, Throwable t) {
        return filter(Thread.currentThread());
    }

    public Result filter(Logger logger, Level level, Marker marker, Message msg, Throwable t) {
        return filter(Thread.currentThread());
    }

    @Override
    public Result filter(LogEvent event) {
        return filter(Thread.currentThread());
    }

    public Result filter(Thread thread) {
        if(thread.getName()==null) return onMismatch;
        return thread.getName().indexOf(threadName)>=0 ? onMatch : onMismatch;
    }

    @Override
    public String toString() {
        return threadName.toString();
    }

    @PluginFactory
    public static ThreadFilter createFilter(@PluginAttribute("threadName") String threadName,
                                            @PluginAttribute("onMatch") final Result match,
                                            @PluginAttribute("onMismatch") final Result mismatch) {
        return new ThreadFilter(threadName, match, mismatch);
    }
}
