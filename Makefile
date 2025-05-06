.PHONY: clean
.PHONY: FORCE

LATEX=pdflatex -shell-escape
BIBTEX=bibtex
SOURCES=*.tex section/*.tex
REFS=bib/*.bib
FIGS=figure/*.eps
CODE=code/*.java
MAIN=paper
SUPP=supp
PY3 := $(shell command -v python3 2> /dev/null)

all: $(MAIN).pdf

ifdef PY3
$(MAIN).pdf: FORCE
	@python3 bin/latexrun --latex-args="-shell-escape" $(MAIN)
else
$(MAIN).pdf: $(SOURCES) $(REFS) $(FIGS) $(CODE)
	$(LATEX)  $(MAIN)
	$(BIBTEX) $(MAIN)
	$(LATEX)  $(MAIN)
	$(LATEX)  $(MAIN)
endif

supp: $(SUPP).pdf

ifdef PY3
$(SUPP).pdf: FORCE
	@python3 bin/latexrun --latex-args="-shell-escape" $(SUPP)
else
$(SUPP).pdf: supp.tex section/supp.tex $(FIGS) 
	$(LATEX)  supp.tex
endif

clean:
	$(RM) -rf *.out *.log *.pbm *.ps $(MAIN).pdf *.dvi *.bbl *.blg *.aux *.4ct *.4tc *.css *.html *.idv *.lg *.xref *.tmp *.pyg _minted-*/
