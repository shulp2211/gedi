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
package gedi.core.data.annotation;


import java.io.IOException;

import cern.colt.Arrays;
import gedi.util.io.randomaccess.BinaryReader;
import gedi.util.io.randomaccess.BinaryWriter;
import gedi.util.io.randomaccess.serialization.BinarySerializable;

public class EmptyAnnotation implements BinarySerializable {
	
	public static EmptyAnnotation instance = new EmptyAnnotation();
	
	
	public EmptyAnnotation() {
	}

	@Override
	public void serialize(BinaryWriter out) throws IOException {
	}

	@Override
	public void deserialize(BinaryReader in) throws IOException {
	}
	
	@Override
	public String toString() {
		return "";
	}

}
