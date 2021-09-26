package ru.segezhagroup.ssc.portal;

import com.atlassian.jira.avatar.Avatar;
import com.atlassian.jira.avatar.AvatarService;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.util.I18nHelper;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.plugin.web.model.WebPanel;
import org.apache.commons.lang.StringEscapeUtils;

import java.io.IOException;
import java.io.Writer;
import java.net.URI;
import java.util.Map;


public class ShowAssignee implements WebPanel {

    @ComponentImport
    private final AvatarService avatarService;

    @ComponentImport
    private final I18nHelper i18n;

    public ShowAssignee(AvatarService avatarService, I18nHelper i18n) {
        this.avatarService = avatarService;
        this.i18n = i18n;
    }

    @Override
    public String getHtml(Map<String, Object> map) {
        Issue issue = (Issue)map.get("issue");
        if (issue.getAssignee() != null) {
            ApplicationUser currentUser = (ApplicationUser)map.get("user");
            String assignee = StringEscapeUtils.escapeHtml(issue.getAssignee().getDisplayName());
            URI absoluteUrl = this.avatarService.getAvatarAbsoluteURL(currentUser, issue.getAssignee(), Avatar.Size.MEDIUM);
            return "<ul class=\"cv-user-list\"><li><span class=\"sd-user sd-user-tagged\" title=\"" + assignee + "\"><span class=\"aui-avatar aui-avatar-small sd-user-cell sd-avatar-cell\"><span class=\"aui-avatar-inner\"><img src=\"" + absoluteUrl + "\"></span></span><span class=\"sd-user-cell\"><span class=\"sd-user-value\">" + assignee + "</span><span class=\"sd-user-tag\" title=\"Assignee\">" + this.i18n.getText("issue.field.assignee") + "</span></span></span></li></ul>";
        }
        return "";    }

    @Override
    public void writeHtml(Writer writer, Map<String, Object> map) throws IOException {

    }
}
