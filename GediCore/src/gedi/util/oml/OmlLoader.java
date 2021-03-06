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
package gedi.util.oml;

import gedi.core.workspace.loader.WorkspaceItemLoader;

import java.io.IOException;
import java.nio.file.Path;

public class OmlLoader<T> implements WorkspaceItemLoader<T,Void> {

	private static String[] extensions = {"oml","oml.jhp"};
	
	@Override
	public String[] getExtensions() {
		return extensions;
	}

	@Override
	public T load(Path path) throws IOException {
		return Oml.create(path.toString());
	}

	@Override
	public Class<T> getItemClass() {
		return (Class<T>)Object.class;
	}

	@Override
	public boolean hasOptions() {
		return false;
	}

	@Override
	public void updateOptions(Path path) {
		
	}

	@Override
	public Void preload(Path path) throws IOException {
		return null;
	}

}
