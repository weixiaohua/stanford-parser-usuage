import java.io.StringReader;
import java.util.List;

import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.Label;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.Tokenizer;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreePrint;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import edu.stanford.nlp.trees.TypedDependency;


public class PaserTest {
	public static void main(String[] args) {
		 String parserModel = "edu/stanford/nlp/models/lexparser/chineseFactored.ser.gz";
		 LexicalizedParser lp = LexicalizedParser.loadModel(parserModel);
		 demoAPI(lp);
	}
	/**
	   * demoAPI demonstrates other ways of calling the parser with
	   * already tokenized text, or in some cases, raw text that needs to
	   * be tokenized as a single sentence.  Output is handled with a
	   * TreePrint object.  Note that the options used when creating the
	   * TreePrint can determine what results to print out.  Once again,
	   * one can capture the output by passing a PrintWriter to
	   * TreePrint.printTree. This code is for English.
	   */
	  public static void demoAPI(LexicalizedParser lp) {
	    // This option shows parsing a list of correctly tokenized words
	   // String[] sent = { "This", "is", "an", "easy", "sentence", "." };//涉及 一种 组织 工程化 周围神经 产品 及 制备 方法
		  String str = "组织工程化周围神经产品由神经导管和神经胶质细胞或干细胞构建而成";
		  List<Term> term = ToAnalysis.parse(str);
		  String[] sent = new String[term.size()];
		  System.out.println(term.toString());
		  for (int i = 0; i < term.size(); i++) {
			sent[i] = term.get(i).getRealName();
		}
		  
	    //String[] sent = { "涉及", "一种", "an", "easy", "sentence", "." };
	    List<CoreLabel> rawWords = Sentence.toCoreLabelList(sent);
	    Tree parse = lp.apply(rawWords);
	    parse.pennPrint();
	    System.out.println();
	    treePrinter(parse);
	    // This option shows loading and using an explicit tokenizer
	   /* String sent2 = "This is another sentence.";
	    TokenizerFactory<CoreLabel> tokenizerFactory =
	        PTBTokenizer.factory(new CoreLabelTokenFactory(), "");
	    Tokenizer<CoreLabel> tok =
	        tokenizerFactory.getTokenizer(new StringReader(sent2));
	    List<CoreLabel> rawWords2 = tok.tokenize();
	    parse = lp.apply(rawWords2);

	    TreebankLanguagePack tlp = lp.treebankLanguagePack(); // PennTreebankLanguagePack for English
	    GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
	    GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);
	    List<TypedDependency> tdl = gs.typedDependenciesCCprocessed();
	    System.out.println(tdl);
	    System.out.println();

	    // You can also use a TreePrint object to print trees and dependencies
	    TreePrint tp = new TreePrint("penn,typedDependenciesCollapsed");
	    tp.printTree(parse);*/
	  }
	  
	public static void treePrinter(Tree parse) {
		Tree[] kids = parse.children();
		if (parse.score() == 1.0) {
			// we set the sinal
		}
		if (parse.depth() == 2 && parse.value().equals("NP")) {
			System.out.println("NP : " + parse.numChildren() + "_"+ parse.getLeaves().toString() + "_" + parse.score());
			if (parse.numChildren() == 1) {
				// one word
			}
			
			//much words
			int count = 0;
			for (Tree tree : parse.getLeaves()) {
				if (tree.value().equals("NN")) {
					count++;
				}
			}
			if (count != parse.getLeaves().size()) {
				// has CC need to separate
			}
			return;
		}
		//parse.siblings(parse); brothers
		// parse.parent();//father
		// parse.setScore(1.0);;
		for (Tree tree : kids) {
			// System.out.println(tree.nodeString()+"_"+tree.depth()+"_"+tree.value()+"_"+tree.isLeaf());
			if (tree.isLeaf())
				return;
			treePrinter(tree);
		}
	}
}
