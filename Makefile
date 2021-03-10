# Sample Makefile for the WACC Compiler lab: edit this to build your own comiler
# Locations

ANTLR_DIR	:= antlr_config
SOURCE_DIR	:= src/main/java
OUTPUT_DIR	:= bin

# Tools

ANTLR	:= antlrBuild
FIND	:= find
RM	:= rm -rf
MKDIR	:= mkdir -p
JAVA	:= java
JAVAC	:= javac

JFLAGS	:= -sourcepath $(SOURCE_DIR) -d $(OUTPUT_DIR) -cp lib/antlr-4.9.1-complete.jar 

# the make rules

all: rules package

# runs the antlr build script then attempts to compile all .java files within src
rules:
	cd $(ANTLR_DIR) && ./$(ANTLR) 
	$(FIND) $(SOURCE_DIR) -name '*.java' > $@
	$(MKDIR) $(OUTPUT_DIR)
	$(JAVAC) $(JFLAGS) @$@
	$(RM) rulesd

package:
	cd lib && jar cvf waccLexerParser.jar ../$(OUTPUT_DIR)/antlr/WaccLexer.class ../$(OUTPUT_DIR)/antlr/WaccParser.class

clean:
	$(RM) rules $(OUTPUT_DIR) $(SOURCE_DIR)/antlr
	mvn clean
	$(RM) lib/waccLexerParser.jar

test:
	mvn test

.PHONY: all rules package test clean