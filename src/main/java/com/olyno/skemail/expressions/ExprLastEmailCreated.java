package com.olyno.skemail.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.olyno.skemail.scopes.ScopeEmailCreation;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import javax.mail.Message;

@Name("Last Email Created")
@Description("Returns the email that was last made in a email scope")
@Examples({
        "set {_email} to the last email created"
})
@Since("1.0")

public class ExprLastEmailCreated extends SimpleExpression<Message> {

    static {
        Skript.registerExpression(ExprLastEmailCreated.class, Message.class, ExpressionType.SIMPLE,
                "[the] last[ly] [e]mail (made|created|did)");
    }

    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, SkriptParser.@NotNull ParseResult parser) {
        return true;
    }

    @Override
    protected Message @NotNull [] get(@NotNull Event e) {
        return new Message[]{ScopeEmailCreation.lastEmail};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public @NotNull Class<? extends Message> getReturnType() {
        return Message.class;
    }

    @Override
    public @NotNull String toString(Event e, boolean debug) {
        return "last email created";
    }

}