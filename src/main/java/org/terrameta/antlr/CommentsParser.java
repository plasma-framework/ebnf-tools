package org.terrameta.antlr;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommentsParser {
    public static final String RULE_NAME_PATTERN = "<(.*?)>";
    public static final String COMMENT_BOUNDARY_PATTERN = "/\\*|\\*/";

    public static Optional<String> ruleName(String comment) {
        Matcher matcher = patternMatcher(comment, RULE_NAME_PATTERN);

        if (matcher.find()) {
        	Optional<String> rule = Optional.of(matcher.group(1));
        	System.out.println("RULE MATCH in COMMENT: " + rule);
            return rule;
        }
        return Optional.absent();
    }

    public static String ruleDescription(String comment) {
        return comment.replaceAll(RULE_NAME_PATTERN, "").replaceAll(COMMENT_BOUNDARY_PATTERN, "").trim();
    }

    public static List<String> comments(String input) {
        List<String> comments = Lists.newArrayList();

        Matcher commentMatcher = patternMatcher(input, "/\\*(.)*?\\*/");

        while (commentMatcher.find()) {
            comments.add(commentMatcher.group());

        }
        return comments;
    }

    public static Map<String, String> commentsMap(String input) {
        HashMap<String, String> commentsMap = Maps.newHashMap();
        List<String> comments = comments(input);
        for (String comment : comments) {
            Optional<String> ruleName = ruleName(comment);
            if (ruleName.isPresent()) {
            	String key = ruleName.get();
            	String value = ruleDescription(comment);
            	System.out.println("COMMENT: '" + key + "' VALUE: '" + value + "'");
                commentsMap.put(key, value);
            }
        }
        return commentsMap;
    }

    private static Matcher patternMatcher(String input, String regex) {
        Pattern commentsPattern = Pattern.compile(regex,Pattern.MULTILINE | Pattern.DOTALL);
        return commentsPattern.matcher(input);
    }
}
