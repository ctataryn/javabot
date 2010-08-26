package javabot.commands;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import javabot.BotEvent;
import javabot.Javabot;
import javabot.Message;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public abstract class BaseCommand implements Command {
    private static final Logger log = LoggerFactory.getLogger(BaseCommand.class);
    @Autowired
    ApplicationContext context;

    public abstract void execute(final List<Message> responses, Javabot bot, BotEvent event);

    public String getUsage() {
        final Options options = getOptions();
        final StringBuilder builder = new StringBuilder("usage: " + getCommandName());
        for (final Object o : options.getOptions()) {
            final Option option = (Option) o;
            builder.append(" ");
            if (!option.isRequired()) {
                builder.append(" [");
            }
            builder.append("--" + option.getOpt());
            if (option.getValue() != null) {
                builder.append("=" + option.getValue());
            } else {
                builder.append("=<" + option.getOpt() + ">");
            }
            if (!option.isRequired()) {
                builder.append("]");
            }
        }
        return builder.toString();
    }

    public Options getOptions() {
        final Options options = new Options();
        for (final Field field : getClass().getDeclaredFields()) {
            final Param annotation = field.getAnnotation(Param.class);
            if (annotation != null) {
                final String name = "".equals(annotation.name()) ? field.getName() : annotation.name();
                final String value = !StringUtils.isEmpty(annotation.defaultValue()) ? annotation.defaultValue() : null;
                final Option option = new Option(name, true, null) {
                    @Override
                    public String getValue() {
                        final String optValue = super.getValue();
                        return optValue == null ? value : optValue;
                    }
                };
                option.setRequired(annotation.required());
                options.addOption(option);
            }
        }
        return options;
    }

    public void parse(final List<String> args) throws ParseException {
        final Options options = getOptions();
        final CommandLineParser parser = new GnuParser();
        final CommandLine line = parser.parse(options, args.toArray(new String[args.size()]));
        final Iterator iterator = line.iterator();
        try {
            while (iterator.hasNext()) {
                final Option option = (Option) iterator.next();
                getClass().getDeclaredField(option.getOpt()).set(this, option.getValue());
            }
        } catch (NoSuchFieldException e) {
            log.error(e.getMessage(), e);
            throw new ParseException(e.getMessage());
        } catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
            throw new ParseException(e.getMessage());
        }
    }

    public String getCommandName() {
        String name = getClass().getSimpleName();
        name = name.substring(0, 1).toLowerCase() + name.substring(1);
        return name;
    }

    public static List<Command> listKnownCommands() {
        final ServiceLoader<Command> loader = ServiceLoader.load(Command.class);
        final List<Command> list = new ArrayList<Command>();
        for (final Command command : loader) {
            list.add(command);
        }
        return list;
    }
}