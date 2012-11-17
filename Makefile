.PHONY: all clean

BINDIR=./bin/

all: Visualizer

Visualizer: | $(BINDIR)
	javac -d bin/ -cp bin/ src/*/*.java

run:
	java -cp bin/ graphics.Visualizer | java -cp bin/:jfreechart/lib/jfreechart-1.0.14.jar:jfreechart/lib/jcommon-1.0.17.jar graphics.Plotter

$(BINDIR):
	mkdir -p $(BINDIR)

clean:
	rm -rf $(BINDIR)
