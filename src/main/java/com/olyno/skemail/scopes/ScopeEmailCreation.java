package com.olyno.skemail.scopes;

import ch.njol.skript.Skript;
import ch.njol.skript.config.SectionNode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.Section;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.TriggerItem;
import ch.njol.util.Kleenean;
import com.olyno.skemail.effects.EffConnection;
import com.olyno.skemail.expressions.ExprAttachFilesOfEmail;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;

@Name("Scope Email Creation")
@Description("Scope for mail creation")
@Examples({
        "make new email:",
        "\tset author of email to \"myemail@gmail.com\"",
        "\tset receiver of email to \"receiver@gmail.com\"",
        "\tset object of email to \"Welcome\"",
        "\tset body of email to \"Welcome on my new server!\"",
        "\tset attach file of email to \"plugin/Test/myfile.txt\""
})
@Since("1.0")

public class ScopeEmailCreation extends Section {

    public static Message lastEmail;

    static {
        Skript.registerSection(ScopeEmailCreation.class,
                "(make|do|create) [new] [e]mail [(using|with|for) [account] (%-session%|%-string%)]");
    }

    private Expression<Object> connection;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult, SectionNode sectionNode, List<TriggerItem> triggerItems) {
        loadOptionalCode(sectionNode);
        connection = (Expression<Object>) exprs[0];
        return true;
    }

    @Override
    protected @Nullable TriggerItem walk(Event e) {
        Object c = connection == null ? null : connection.getSingle(e);
        Session session;
        if (c == null) {
            session = EffConnection.lastSession;
        } else if (c instanceof String) {
            session = EffConnection.accounts.get(c);
        } else {
            session = (Session) c;
        }
        lastEmail = new MimeMessage(session);
        EffConnection.lastSession = session;
        ExprAttachFilesOfEmail.files = new ArrayList<>();
        return walk(e, true);
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "make new email " + lastEmail + (connection != null ? " with " + connection.toString(e, debug) : "");
    }

}