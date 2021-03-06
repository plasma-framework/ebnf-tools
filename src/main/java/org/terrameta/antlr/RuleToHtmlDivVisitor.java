package org.terrameta.antlr;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.*;

import static org.terrameta.antlr.ANTLRv4Parser.*;

/**
 * A visitor used to collect all rules from an ANTLR 4 grammar and
 * translate the parse tree into a DSL that the JavaScript library
 * uses to create the SVG for each grammar rule.
 *
 * [1] https://github.com/tabatkins/railroad-diagrams
 */
public class RuleToHtmlDivVisitor extends ANTLRv4ParserBaseVisitor<String> {

	enum EbnfCardinality{
		Optional, ZeroOrMore, OneOrMore;
    }
 		
	 
    // A linked hash-map will guarantee the order of the grammar rules
    // to be the same as they occur inside the grammar.
    //
    // The collection maps all grammar rules from the ANTLR 4 grammar to
    // a DSL that the JavaScript library, `railroad-diagram.js`, uses to
    // translate to SVG-railroad diagrams.
    private final LinkedHashMap<String, String> rules;

    /**
     * Creates a new instance of this visitor. Note that many of the
     * overridden methods are not used: we're only interested in lexer-
     * and parser-rules (and their contents).
     */
    public RuleToHtmlDivVisitor() {
        this.rules = new LinkedHashMap<String, String>();
    }

    //    grammarSpec
    //     : DOC_COMMENT?
    //       grammarType id SEMI
    //       prequelConstruct*
    //       rules
    //       modeSpec*
    //       EOF
    //     ;
    @Override
    public String visitGrammarSpec(@NotNull GrammarSpecContext ctx) {
        return super.visitGrammarSpec(ctx);
    }

    //    grammarType
    //     : ( LEXER GRAMMAR
    //       | PARSER GRAMMAR
    //       | GRAMMAR
    //       )
    //     ;
    @Override
    public String visitGrammarType(@NotNull GrammarTypeContext ctx) {
        return super.visitGrammarType(ctx);
    }

    //    prequelConstruct
    //     : optionsSpec
    //     | delegateGrammars
    //     | tokensSpec
    //     | action
    //     ;
    @Override
    public String visitPrequelConstruct(@NotNull PrequelConstructContext ctx) {
        return super.visitPrequelConstruct(ctx);
    }

    //    optionsSpec
    //     : OPTIONS (option SEMI)* RBRACE
    //     ;
    @Override
    public String visitOptionsSpec(@NotNull OptionsSpecContext ctx) {
        return super.visitOptionsSpec(ctx);
    }

    //    option
    //     : id ASSIGN optionValue
    //     ;
    @Override
    public String visitOption(@NotNull OptionContext ctx) {
        return super.visitOption(ctx);
    }

    //    optionValue
    //     : id (DOT id)*
    //     | STRING_LITERAL
    //     | ACTION
    //     | INT
    //     ;
    @Override
    public String visitOptionValue(@NotNull OptionValueContext ctx) {
        return super.visitOptionValue(ctx);
    }

    //    delegateGrammars
    //     : IMPORT delegateGrammar (COMMA delegateGrammar)* SEMI
    //     ;
    @Override
    public String visitDelegateGrammars(@NotNull DelegateGrammarsContext ctx) {
        return super.visitDelegateGrammars(ctx);
    }

    //    delegateGrammar
    //     : id ASSIGN id
    //     | id
    //     ;
    @Override
    public String visitDelegateGrammar(@NotNull DelegateGrammarContext ctx) {
        return super.visitDelegateGrammar(ctx);
    }

    //    tokensSpec
    //     : TOKENS id (COMMA id)* COMMA? RBRACE
    //     ;
    @Override
    public String visitTokensSpec(@NotNull TokensSpecContext ctx) {
        return super.visitTokensSpec(ctx);
    }

    //    action
    //     : AT (actionScopeName COLONCOLON)? id ACTION
    //     ;
    @Override
    public String visitAction(@NotNull ActionContext ctx) {
        return super.visitAction(ctx);
    }

    //    actionScopeName
    //     : id
    //     | LEXER
    //     | PARSER
    //     ;
    @Override
    public String visitActionScopeName(@NotNull ActionScopeNameContext ctx) {
        return super.visitActionScopeName(ctx);
    }

    //    modeSpec
    //     : MODE id SEMI ruleSpec+
    //     ;
    @Override
    public String visitModeSpec(@NotNull ModeSpecContext ctx) {
        return super.visitModeSpec(ctx);
    }

    //    rules
    //     : ruleSpec*
    //     ;
    @Override
    public String visitRules(@NotNull RulesContext ctx) {
        return super.visitRules(ctx);
    }

    //    ruleSpec
    //     : parserRuleSpec
    //     | lexerRule
    //     ;
    @Override
    public String visitRuleSpec(@NotNull RuleSpecContext ctx) {
        return super.visitRuleSpec(ctx);
    }

    //    parserRuleSpec
    //     : DOC_COMMENT?
    //       ruleModifiers? RULE_REF ARG_ACTION?
    //       ruleReturns? throwsSpec? localsSpec?
    //       rulePrequel*
    //       COLON
    //       ruleBlock
    //       SEMI
    //       exceptionGroup
    //     ;
    @Override
    public String visitParserRuleSpec(@NotNull ParserRuleSpecContext ctx) {

        String ruleName = ctx.RULE_REF().getText();
        StringBuilder buf = new StringBuilder();
        buf.append("<div class=\"divTable\">");
        buf.append("<div class=\"divTableCell\">");
        //String content = "Diagram(" + this.visitRuleBlock(ctx.ruleBlock()) + ").toString()";
        buf.append(this.visitRuleBlock(ctx.ruleBlock()));
        buf.append("</div>");
        buf.append("</div>");
        String content = buf.toString();
        this.rules.put(ruleName, content);

        return content;
    }

    //    exceptionGroup
    //     : exceptionHandler* finallyClause?
    //     ;
    @Override
    public String visitExceptionGroup(@NotNull ExceptionGroupContext ctx) {
        return super.visitExceptionGroup(ctx);
    }

    //    exceptionHandler
    //     : CATCH ARG_ACTION ACTION
    //     ;
    @Override
    public String visitExceptionHandler(@NotNull ExceptionHandlerContext ctx) {
        return super.visitExceptionHandler(ctx);
    }

    //    finallyClause
    //     : FINALLY ACTION
    //     ;
    @Override
    public String visitFinallyClause(@NotNull FinallyClauseContext ctx) {
        return super.visitFinallyClause(ctx);
    }

    //    rulePrequel
    //     : optionsSpec
    //     | ruleAction
    //     ;
    @Override
    public String visitRulePrequel(@NotNull RulePrequelContext ctx) {
        return super.visitRulePrequel(ctx);
    }

    //    ruleReturns
    //     : RETURNS ARG_ACTION
    //     ;
    @Override
    public String visitRuleReturns(@NotNull RuleReturnsContext ctx) {
        return super.visitRuleReturns(ctx);
    }

    //    throwsSpec
    //     : THROWS id (COMMA id)*
    //     ;
    @Override
    public String visitThrowsSpec(@NotNull ThrowsSpecContext ctx) {
        return super.visitThrowsSpec(ctx);
    }

    //    localsSpec
    //     : LOCALS ARG_ACTION
    //     ;
    @Override
    public String visitLocalsSpec(@NotNull LocalsSpecContext ctx) {
        return super.visitLocalsSpec(ctx);
    }

    //    ruleAction
    //     : AT id ACTION
    //     ;
    @Override
    public String visitRuleAction(@NotNull RuleActionContext ctx) {
        return super.visitRuleAction(ctx);
    }

    //    ruleModifiers
    //     : ruleModifier+
    //     ;
    @Override
    public String visitRuleModifiers(@NotNull RuleModifiersContext ctx) {
        return super.visitRuleModifiers(ctx);
    }

    //    ruleModifier
    //     : PUBLIC
    //     | PRIVATE
    //     | PROTECTED
    //     | FRAGMENT
    //     ;
    @Override
    public String visitRuleModifier(@NotNull RuleModifierContext ctx) {
        return super.visitRuleModifier(ctx);
    }

    //    ruleBlock
    //     : ruleAltList
    //     ;
    @Override
    public String visitRuleBlock(@NotNull RuleBlockContext ctx) {
        return this.visitRuleAltList(ctx.ruleAltList());
    }


    //    labeledAlt
    //     : alternative (POUND id)?
    //     ;
    @Override
    public String visitLabeledAlt(@NotNull LabeledAltContext ctx) {
        return this.visitAlternative(ctx.alternative());
    }

    //    lexerRule
    //     : DOC_COMMENT? FRAGMENT?
    //       TOKEN_REF COLON lexerRuleBlock SEMI
    //     ;
    @Override
    public String visitLexerRule(@NotNull LexerRuleContext ctx) {

        String ruleName = ctx.TOKEN_REF().getText();

        String diagram = "Diagram(" + this.visitLexerRuleBlock(ctx.lexerRuleBlock()) + ").toString()";

        this.rules.put(ruleName, diagram);

        return diagram;
    }

    //    lexerRuleBlock
    //     : lexerAltList
    //     ;
    @Override
    public String visitLexerRuleBlock(@NotNull LexerRuleBlockContext ctx) {
        return this.visitLexerAltList(ctx.lexerAltList());
    }

    //    lexerAltList
    //     : lexerAlt (OR lexerAlt)*
    //     ;
    @Override
    public String visitLexerAltList(@NotNull LexerAltListContext ctx) {

        StringBuilder builder = new StringBuilder("Choice(0, ");

        List<LexerAltContext> alts = ctx.lexerAlt();

        for (int i = 0; i < alts.size(); i++) {

            LexerAltContext alt = alts.get(i);

            builder.append(this.visitLexerAlt(alt))
                    .append(this.seperator(alts, i));
        }

        return builder.append(")").toString();
    }

    //    lexerAlt
    //     : lexerElements? lexerCommands?
    //     ;
    @Override
    public String visitLexerAlt(@NotNull LexerAltContext ctx) {
        if (ctx.lexerElements() != null) {
            return this.visitLexerElements(ctx.lexerElements());
        }
        else {
            return "Comment('&#949;')";
        }
    }

    //    lexerElements
    //     : lexerElement+
    //     ;
    @Override
    public String visitLexerElements(@NotNull LexerElementsContext ctx) {

        StringBuilder builder = new StringBuilder("SEQUENCE(");

        List<LexerElementContext> elements = ctx.lexerElement();

        for (int i = 0; i < elements.size(); i++) {

            LexerElementContext element = elements.get(i);

            builder.append(this.visitLexerElement(element))
                    .append(seperator(elements, i));
        }

        return builder.append(")").toString();
    }

    //    lexerElement
    //     : labeledLexerElement ebnfSuffix?
    //     | lexerAtom ebnfSuffix?
    //     | lexerBlock ebnfSuffix?
    //     | ACTION QUESTION?
    //     ;
    @Override
    public String visitLexerElement(@NotNull LexerElementContext ctx) {

        StringBuilder builder = new StringBuilder();
        boolean hasEbnfSuffix = (ctx.ebnfSuffix() != null);

        if (ctx.labeledLexerElement() != null) {
            if (hasEbnfSuffix) {
                builder.append(this.visitEbnfSuffix(ctx.ebnfSuffix()))
                        .append("(")
                        .append(this.visitLabeledLexerElement(ctx.labeledLexerElement()))
                        .append(")");
            }
            else {
                builder.append(this.visitLabeledLexerElement(ctx.labeledLexerElement()));
            }
        }
        else if (ctx.lexerAtom() != null) {
            if (hasEbnfSuffix) {
                builder.append(this.visitEbnfSuffix(ctx.ebnfSuffix()))
                        .append("(")
                        .append(this.visitLexerAtom(ctx.lexerAtom()))
                        .append(")");
            }
            else {
                builder.append(this.visitLexerAtom(ctx.lexerAtom()));
            }
        }
        else if (ctx.lexerBlock() != null) {
            if (hasEbnfSuffix) {
                builder.append(this.visitEbnfSuffix(ctx.ebnfSuffix()))
                        .append("(")
                        .append(this.visitLexerBlock(ctx.lexerBlock()))
                        .append(")");
            }
            else {
                builder.append(this.visitLexerBlock(ctx.lexerBlock()));
            }
        }
        else {
            return "Comment('&#949;')";
        }

        return builder.toString();
    }

    //    labeledLexerElement
    //     : id (ASSIGN|PLUS_ASSIGN)
    //       ( lexerAtom
    //       | block
    //       )
    //     ;
    @Override
    public String visitLabeledLexerElement(@NotNull LabeledLexerElementContext ctx) {

        if (ctx.lexerAtom() != null) {
            return this.visitLexerAtom(ctx.lexerAtom());
        }
        else {
            return this.visitBlock(ctx.block());
        }
    }

    //    lexerBlock
    //     : LPAREN lexerAltList RPAREN
    //     ;
    @Override
    public String visitLexerBlock(@NotNull LexerBlockContext ctx) {
        return this.visitLexerAltList(ctx.lexerAltList());
    }

    //    lexerCommands
    //     : RARROW lexerCommand (COMMA lexerCommand)*
    //     ;
    @Override
    public String visitLexerCommands(@NotNull LexerCommandsContext ctx) {
        return super.visitLexerCommands(ctx);
    }

    //    lexerCommand
    //     : lexerCommandName LPAREN lexerCommandExpr RPAREN
    //     | lexerCommandName
    //     ;
    @Override
    public String visitLexerCommand(@NotNull LexerCommandContext ctx) {
        return super.visitLexerCommand(ctx);
    }

    //    lexerCommandName
    //     : id
    //     | MODE
    //     ;
    @Override
    public String visitLexerCommandName(@NotNull LexerCommandNameContext ctx) {
        return super.visitLexerCommandName(ctx);
    }

    //    lexerCommandExpr
    //     : id
    //     | INT
    //     ;
    @Override
    public String visitLexerCommandExpr(@NotNull LexerCommandExprContext ctx) {
        return super.visitLexerCommandExpr(ctx);
    }
    
    //    ruleAltList
    //     : labeledAlt (OR labeledAlt)*
    //     ;
    @Override
    public String visitRuleAltList(@NotNull RuleAltListContext ctx) {

        //StringBuilder builder = new StringBuilder("Choice(0, ");
        StringBuilder builder = new StringBuilder("");
        List<LabeledAltContext> alternatives = ctx.labeledAlt();
        if (alternatives.size() > 1) {
    		builder.append("<div class=\"divTable\">");
    		builder.append("<div class=\"divTableCell\">");
    		for (int i = 0; i < alternatives.size(); i++) {
    			LabeledAltContext alternative = alternatives.get(i);
    			String token = this.visitLabeledAlt(alternative);
    			if (token.contains("DEFERRED")) {
    				int foo = 0;
    				foo++;
     			}
       			System.out.println(token);
   			    if (i > 0) {
    				builder.append("</div><div class=\"divTableCell\">");
    				builder.append("<div class=\"divTableCell\"><b>...</b></div>");
    				builder.append("<div class=\"divTableCell\"><b>|</b></div>");
    				builder.append("<div class=\"divTableCell\">");
    				builder.append(token);
    				// .append(comma(alternatives, i));
    				builder.append("</div>");
    			} else {
    				builder.append("<div class=\"divTableCell\"><b>...</b></div>");
    				builder.append("<div class=\"divTableCell\"><b></b></div>");
    				builder.append("<div class=\"divTableCell\">");
    				builder.append(token);
    				// .append(comma(alternatives, i));
    				builder.append("</div>");
    			}
    		}
    		builder.append("</div>");
    		builder.append("</div>");
        }
        else {
            LabeledAltContext alternative = alternatives.get(0);
            builder.append(this.visitLabeledAlt(alternative));
        }

        //return builder.append(")").toString();
        return builder.toString();
    }
    

    //    altList
    //     : alternative (OR alternative)*
    //     ;
    @Override
    public String visitAltList(@NotNull AltListContext ctx) {
         StringBuilder builder = new StringBuilder("");
        List<AlternativeContext> alternatives = ctx.alternative();
        if (alternatives.size() > 1) {
        	
            boolean allTerminal = true;
    		for (int i = 0; i < alternatives.size(); i++) {
    			AlternativeContext alternative = alternatives.get(i);
    			String token = this.visitAlternative(alternative);
    			if (token.contains("DEFERRED")) {
    				int foo = 0;
    				foo++;
    				//this.visitAlternative(alternative);
     			}
        	    if (!isTerminal(alternative)) {
        	    	allTerminal = false;
        	    }
    		}
    		//if (!allTerminal) {
    		builder.append("<div class=\"divTable\">");
    		builder.append("<div class=\"divTable\">");
    		//}
    		for (int i = 0; i < alternatives.size(); i++) {
    			AlternativeContext alternative = alternatives.get(i);
    			String token = this.visitAlternative(alternative);
    			if (token.contains("DEFERRED")) {
    				int foo = 0;
    				foo++;
    				//this.visitAlternative(alternative);
     			}
    			System.out.println(token);
    			if (i > 0) {
    				if (!allTerminal) {
       				    builder.append("</div><div class=\"divTableCell\">");
    				    builder.append("<div class=\"divTableCell\"><b>...</b></div>");
    				}
    				builder.append("<div class=\"divTableCell\"><b>|</b></div>");
    				builder.append("<div class=\"divTableCell\">");
    				builder.append(token);
    				// .append(comma(alternatives, i));
    				builder.append("</div>");
    			} else {
    				if (!allTerminal) {
     				builder.append("<div class=\"divTableCell\"><b>...</b></div>");
    				}
    				builder.append("<div class=\"divTableCell\"><b></b></div>");
    				builder.append("<div class=\"divTableCell\">");
    				builder.append(token);
    				// .append(comma(alternatives, i));
    				builder.append("</div>");
    			}
    		}
    		//if (!allTerminal) {
    		builder.append("</div>");
    		builder.append("</div>");
    		//}
        }
        else {
        	AlternativeContext alternative = alternatives.get(0);
            builder.append(this.visitAlternative(alternative));
      	
        }

        //return builder.append(")").toString();
        return builder.toString();
      }
    
    private boolean isTerminal(AlternativeContext alternative) {
    	if (alternative.children.size() == 1) {
    		ParseTree c1 = alternative.children.get(0);
    		if (ANTLRv4Parser.ElementsContext.class.isAssignableFrom(c1.getClass())) {
    	    	if (c1.getChildCount() == 1) {
    	    		ParseTree c2 = c1.getChild(0);
    	    		if (ANTLRv4Parser.ElementContext.class.isAssignableFrom(c2.getClass())) {
    	    	    	if (c2.getChildCount() == 1) {
       	    		        ParseTree c3 = c2.getChild(0);
        	    		    if (ANTLRv4Parser.AtomContext.class.isAssignableFrom(c3.getClass())) {
            	    	    	if (c3.getChildCount() == 1) {
               	    		        ParseTree c4 = c3.getChild(0);
                	    		    if (ANTLRv4Parser.TerminalContext.class.isAssignableFrom(c4.getClass())) {
                	    			   return true;
                	    		    }
            	    	    	}
       	    			   
        	    		    }
    	    	    	}
   	    			
    	    		}
    	    				 
    	    	}
   			
    		}
    				 
    	}
    	return false;
    }
  
    //    alternative
    //     : elements
    //     | // empty alt
    //     ;
    @Override
    public String visitAlternative(@NotNull AlternativeContext ctx) {
        if (ctx.elements() != null) {
            return this.visitElements(ctx.elements());
        }
        else {
            return "Comment('&#949;')";
        }
    }

    //    elements
    //     : element+
    //     ;
    @Override
    public String visitElements(@NotNull ElementsContext ctx) {

        //StringBuilder builder = new StringBuilder("SEQUENCE(");
        StringBuilder builder = new StringBuilder("");
        List<ElementContext> elements = ctx.element();

        for (int i = 0; i < elements.size(); i++) {

            ElementContext element = elements.get(i);

            builder.append(this.visitElement(element))
                    .append(seperator(elements, i));
        }

        //return builder.append(")").toString();
        return builder.toString();
    }

    //    element
    //     : labeledElement
    //       ( ebnfSuffix
    //       |
    //       )
    //     | atom
    //       ( ebnfSuffix
    //       |
    //       )
    //     | ebnf
    //     | ACTION QUESTION?
    //     ;
    @Override
    public String visitElement(@NotNull ElementContext ctx) {

        boolean hasEbnfSuffix = (ctx.ebnfSuffix() != null);

        if (ctx.labeledElement() != null) {
            if (hasEbnfSuffix) {
            	EbnfCardinality card = getEbnfCardinality(ctx.ebnfSuffix());
            	switch (card) {
				case OneOrMore:
					return "{ "  + this.visitLabeledElement(ctx.labeledElement()) + " }";
 				case Optional:
					return "[ "  + this.visitLabeledElement(ctx.labeledElement()) + " ]";
 				case ZeroOrMore:
					return "[ "  + this.visitLabeledElement(ctx.labeledElement()) + " ]";
 				default:
					return "[ "  + this.visitLabeledElement(ctx.labeledElement()) + " ]";
             	}
//                return this.visitEbnfSuffix(ctx.ebnfSuffix()) +
//                        "("  + this.visitLabeledElement(ctx.labeledElement()) + ")";
            }
            else {
                return this.visitLabeledElement(ctx.labeledElement());
            }
        }
        else if (ctx.atom() != null) {
            if (hasEbnfSuffix) {
            	EbnfCardinality card = getEbnfCardinality(ctx.ebnfSuffix());
            	switch (card) {
				case OneOrMore:
					return "{ "  + this.visitAtom(ctx.atom()) + " }";
 				case Optional:
					return "[ "  + this.visitAtom(ctx.atom()) + " ]";
 				case ZeroOrMore:
					return "[ "  + this.visitAtom(ctx.atom()) + " ]";
 				default:
					return "[ "  + this.visitAtom(ctx.atom()) + " ]";
             	}
//               return this.visitEbnfSuffix(ctx.ebnfSuffix()) +
//                        "("  + this.visitAtom(ctx.atom()) + ")";
            }
            else {
                return this.visitAtom(ctx.atom());
            }
        }
        else if (ctx.ebnf() != null) {
             return this.visitEbnf(ctx.ebnf());
        }
        else if (ctx.QUESTION() != null) {
            return "Comment('predicate')";
        }
        else {
            return "Comment('&#949;')";
        }
    }

    //    labeledElement
    //     : id (ASSIGN|PLUS_ASSIGN)
    //       ( atom
    //       | block
    //       )
    //     ;
    @Override
    public String visitLabeledElement(@NotNull LabeledElementContext ctx) {
        if (ctx.atom() != null) {
            return this.visitAtom(ctx.atom());
        }
        else {
            return this.visitBlock(ctx.block());
        }
    }

    //    ebnf
    //     : block blockSuffix?
    //     ;
    @Override
    public String visitEbnf(@NotNull EbnfContext ctx) {
        if (ctx.blockSuffix() != null) {
        	EbnfCardinality card = getEbnfCardinality(ctx.blockSuffix().ebnfSuffix());
        	switch (card) {
			case OneOrMore:
				return "{ "  + this.visitBlock(ctx.block()) + " }";
			case Optional:
				return "[ "  + this.visitBlock(ctx.block()) + " ]";
			case ZeroOrMore:
				return "[ "  + this.visitBlock(ctx.block()) + " ]";
			default:
				return "[ "  + this.visitBlock(ctx.block()) + " ]";
         	}
        	
//            return this.visitBlockSuffix(ctx.blockSuffix()) +
//                    "(" + this.visitBlock(ctx.block()) + ")";
        }
        else {
            return this.visitBlock(ctx.block());
        }
    }

    //    blockSuffix
    //     : ebnfSuffix // Standard EBNF
    //     ;
    @Override
    public String visitBlockSuffix(@NotNull BlockSuffixContext ctx) {
        return this.visitEbnfSuffix(ctx.ebnfSuffix());
    }

    //    ebnfSuffix
    //     : QUESTION QUESTION?
    //     | STAR QUESTION?
    //     | PLUS QUESTION?
    //     ;
    @Override
    public String visitEbnfSuffix(@NotNull EbnfSuffixContext ctx) {

        String text = ctx.getText();

        if (text.equals("?")) {
            return "Optional";
        }
        else if (text.equals("*")) {
            return "ZeroOrMore";
        }
        else {
            return "OneOrMore";
        }
    }
    
    public EbnfCardinality getEbnfCardinality(@NotNull EbnfSuffixContext ctx) 
    {
        String text = ctx.getText();
    	    	
        if (text.equals("?")) {
            return EbnfCardinality.Optional;
        }
        else if (text.equals("*")) {
            return EbnfCardinality.ZeroOrMore;
        }
        else {
            return EbnfCardinality.OneOrMore;
        }
    }

    //    lexerAtom
    //     : range
    //     | terminal
    //     | RULE_REF
    //     | notSet
    //     | LEXER_CHAR_SET
    //     | DOT elementOptions?
    //     ;
    @Override
    public String visitLexerAtom(@NotNull LexerAtomContext ctx) {

        if (ctx.range() != null) {
            return this.visitRange(ctx.range());
        }
        else if (ctx.terminal() != null) {
            return this.visitTerminal(ctx.terminal());
        }
        else if (ctx.RULE_REF() != null) {
            return this.visitTerminal(ctx.RULE_REF());
        }
        else if (ctx.notSet() != null) {
            return this.visitNotSet(ctx.notSet());
        }
        else if (ctx.LEXER_CHAR_SET() != null) {
            return this.visitTerminal(ctx.LEXER_CHAR_SET());
        }
        else {
            return "Terminal('any char')";
        }
    }

    //    atom
    //     : range
    //     | terminal
    //     | ruleref
    //     | notSet
    //     | DOT elementOptions?
    //     ;
    @Override
    public String visitAtom(@NotNull AtomContext ctx) {

        if (ctx.range() != null) {
            return this.visitRange(ctx.range());
        }
        else if (ctx.terminal() != null) {
            return this.visitTerminal(ctx.terminal());
        }
        else if (ctx.ruleref() != null) {
            return this.visitRuleref(ctx.ruleref());
        }
        else if (ctx.notSet() != null) {
            return this.visitNotSet(ctx.notSet());
        }
        else {
            return "NonTerminal('any token')";
        }
    }

    //    notSet
    //     : NOT setElement
    //     | NOT blockSet
    //     ;
    @Override
    public String visitNotSet(@NotNull NotSetContext ctx) {
        if (ctx.setElement() != null) {
            return "Sequence(Comment('not'), " + this.visitSetElement(ctx.setElement()) + ")";
        }
        else {
            return "Sequence(Comment('not'), " + this.visitBlockSet(ctx.blockSet()) + ")";
        }
    }

    //    blockSet
    //     : LPAREN setElement (OR setElement)* RPAREN
    //     ;
    @Override
    public String visitBlockSet(@NotNull BlockSetContext ctx) {

        StringBuilder builder = new StringBuilder("Choice(0, ");
        List<SetElementContext> elements = ctx.setElement();

        for (int i = 0; i < elements.size(); i++) {

            SetElementContext element = elements.get(i);

            builder.append(this.visitSetElement(element))
                    .append(seperator(elements, i));
        }

        return builder.append(")").toString();
    }

    //    setElement
    //     : TOKEN_REF
    //     | STRING_LITERAL
    //     | range
    //     | LEXER_CHAR_SET
    //     ;
    @Override
    public String visitSetElement(@NotNull SetElementContext ctx) {
        return super.visitSetElement(ctx);
    }

    //    block
    //     : LPAREN
    //       ( optionsSpec? ruleAction* COLON )?
    //       altList
    //       RPAREN
    //     ;
    @Override
    public String visitBlock(@NotNull BlockContext ctx) {
        return this.visitAltList(ctx.altList());
    }

    //    ruleref
    //     : RULE_REF ARG_ACTION?
    //     ;
    @Override
    public String visitRuleref(@NotNull RulerefContext ctx) {
        return this.visitTerminal(ctx.RULE_REF());
    }

    //    range
    //     : STRING_LITERAL RANGE STRING_LITERAL
    //     ;
    @Override
    public String visitRange(@NotNull RangeContext ctx) {

        return String.format("'%s .. %s'",
                this.escapeTerminal(ctx.STRING_LITERAL(0)),
                this.escapeTerminal(ctx.STRING_LITERAL(1))
        );
    }

    //    terminal
    //     : TOKEN_REF elementOptions?
    //     | STRING_LITERAL elementOptions?
    //     ;
    @Override
    public String visitTerminal(@NotNull TerminalContext ctx) {
        if (ctx.TOKEN_REF() != null) {
            return this.visitTerminal(ctx.TOKEN_REF());
        }
        else {
            return this.visitTerminal(ctx.STRING_LITERAL());
        }
    }

    //    elementOptions
    //     : LT elementOption (COMMA elementOption)* GT
    //     ;
    @Override
    public String visitElementOptions(@NotNull ElementOptionsContext ctx) {
        return super.visitElementOptions(ctx);
    }

    //    elementOption
    //     : // This format indicates the default node option
    //       id
    //     | // This format indicates option assignment
    //       id ASSIGN (id | STRING_LITERAL)
    //     ;
    @Override
    public String visitElementOption(@NotNull ElementOptionContext ctx) {
        return super.visitElementOption(ctx);
    }

    //    id
    //     : RULE_REF
    //     | TOKEN_REF
    //     ;
    @Override
    public String visitId(@NotNull IdContext ctx) {
        return super.visitId(ctx);
    }

    public String getDiagram(String ruleName) {
        return this.rules.get(ruleName);
    }

    public Map<String, String> getRules() {
        return new LinkedHashMap<String, String>(this.rules);
    }

    private String escapeTerminal(TerminalNode node) {

        String text = node.getText();

        String escaped = text.replace("\\u", "\\\\u");

        switch (node.getSymbol().getType()) {
            case ANTLRv4Lexer.STRING_LITERAL:
                return "\\'" + escaped.substring(1, escaped.length() - 1) + "\\'";
            default:
                return escaped.replace("'", "\\'");
        }
    }

    private String seperator(Collection<?> collection, int index) {
        return index < collection.size() - 1 ? " " : "";
    }

    @Override
    public String visitTerminal(@NotNull TerminalNode node) {

        switch (node.getSymbol().getType()) {

            case ANTLRv4Lexer.STRING_LITERAL:
            case ANTLRv4Lexer.LEXER_CHAR_SET:
            	String token = this.escapeTerminal(node);
            	if ("DEFERRED".equals(token)) {
            		token = "QQQQ" +token;
             	}
            	if (token.startsWith("K_")) {
            		token = token.substring(2);
                	return "<b>" + token + "</b>";
            	}
            	else if (token.startsWith("\\'") && token.endsWith("\\'")) {
            		token = token.replace("\\", "");
                	return "<b>" + token + "</b>";
            	}
            	return "<a href=\"#\">" + token + "</a>";
                //return "Terminal('" + this.escapeTerminal(node) + "')";

            case ANTLRv4Lexer.TOKEN_REF:
            	token = node.getText();
            	if ("DEFERRED".equals(token)) {
            		token = "EEEE" +token;
            	}
            	if (token.startsWith("K_")) {
            		token = token.substring(2);
                	return "<b>" + token + "</b>";
            	}
            	else if (token.startsWith("\\'") && token.endsWith("\\'")) {
            		token = token.replace("\\", "");
                	return "<b>" + token + "</b>";
            	}
            	return "<a href=\"#\">" + token + "</a>";
               //return "Terminal('" + node.getText() + "')";

            default:
            	token = node.getText();
            	if ("DEFERRED".equals(token)) {
            		token = "FFFF" +token;
            	}
            	if (token.startsWith("K_")) {
            		token = token.substring(2);
                	return "<b>" + token + "</b>";
            	}
            	else if (token.startsWith("\\'") && token.endsWith("\\'")) {
            		token = token.replace("\\", "");
               	    return "<b>" + token + "</b>";
            	}
            	return "<a href=\"#\">" + token + "</a>";
                //return "NonTerminal('" + node.getText() + "')";
                
                


        }
    }
}
