/**
 * 
 *    Copyright 2017 Florian Erhard
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * 
 */
package gedi.util.datastructure.tree.suffixTree.tree.index;

import gedi.util.datastructure.tree.suffixTree.tree.SuffixTree;
import gedi.util.datastructure.tree.suffixTree.tree.traversal.DfsDownTraverser;
import gedi.util.datastructure.tree.suffixTree.tree.traversal.Traverser;

public class TextDepthIndexer extends AbstractIntIndexer {

	@Override
	public String name() {
		return "IndexTextDepth";
	}

	@Override
	protected void createNew_internal(SuffixTree tree, int[] index) {
		DfsDownTraverser t = new DfsDownTraverser(tree,tree.getRoot().getNode());
		while (t.hasNext()) {
			int node = t.nextInt();
			if (node>0 && t.getDirection()==Traverser.DOWN)
				index[node] = index[t.getPrevious()]+tree.getSubSequence(t.getPrevious(), node).length();
		}
	}

}
