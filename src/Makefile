
JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java
classes = \
		  Main.java \
		  Token.java \
		  Scanner.java \
		  Parser.java \
		  IR.java \
default: classes

clean:
	rm -f *.class

%.class : %.java
    $(JAVAC) $<

build:
	javac *.java
	jar cfm lab1-new.jar manifest.txt *.class *.java
