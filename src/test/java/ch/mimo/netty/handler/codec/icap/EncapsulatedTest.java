/*******************************************************************************
 * Copyright (c) 2011 Michael Mimo Moratti.
 *
 * Michael Mimo Moratti licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *******************************************************************************/
package ch.mimo.netty.handler.codec.icap;

import junit.framework.Assert;

import org.junit.Test;

import ch.mimo.netty.handler.codec.icap.Encapsulated.EntryName;

public class EncapsulatedTest extends Assert {

	@Test
	public void testSimpleValueParsing() {
		String parameter = "req-hdr=0, res-hdr=45, req-body=124";
		Encapsulated encapsulated = Encapsulated.parseHeader(parameter);
		assertTrue("req-hdr value is missing",encapsulated.containsEntry(EntryName.REQHDR));
		assertTrue("res-hdr value is missing",encapsulated.containsEntry(EntryName.RESHDR));
		assertTrue("req-body value is missing",encapsulated.containsEntry(EntryName.REQBODY));
	}
	
	@Test
	public void testWhitespaceGap() {
		String parameter = "req-hdr=0,  res-hdr=45, req-body=124";
		Encapsulated encapsulated = Encapsulated.parseHeader(parameter);
		assertTrue("req-hdr value is missing",encapsulated.containsEntry(EntryName.REQHDR));
		assertTrue("res-hdr value is missing",encapsulated.containsEntry(EntryName.RESHDR));
		assertTrue("req-body value is missing",encapsulated.containsEntry(EntryName.REQBODY));
	}
	
	@Test
	public void testIteratorWithWrongSequence() {
		String parameter = "res-hdr=45, req-hdr=0, req-body=124";
		Encapsulated encapsulated = Encapsulated.parseHeader(parameter);
		assertTrue("req-hdr value is missing",encapsulated.containsEntry(EntryName.REQHDR));
		assertTrue("res-hdr value is missing",encapsulated.containsEntry(EntryName.RESHDR));
		assertTrue("req-body value is missing",encapsulated.containsEntry(EntryName.REQBODY));
		EntryName reqhdr = encapsulated.getNextEntry();
		assertEquals("req-hdr was expected",EntryName.REQHDR,reqhdr);
		encapsulated.setProcessed(reqhdr);
		EntryName reshdr = encapsulated.getNextEntry();
		assertEquals("res-hdr was expected",EntryName.RESHDR,reshdr);
		encapsulated.setProcessed(reshdr);
		EntryName reqbody = encapsulated.getNextEntry();
		assertEquals("reqbody was expected",EntryName.REQBODY,reqbody);
		encapsulated.setProcessed(reqbody);
		assertNull("null was expected after last entry is processed",encapsulated.getNextEntry());
	}
	
	@Test
	public void testGarbledString() {
		String parameter = "   req-hdr=0; req-body=124, Whaterver   ";
		boolean error = false;
		try {
			Encapsulated.parseHeader(parameter);
		} catch(Error e) {
			error = true;
		}
		assertTrue("Validation error did not occur",error);
	}
}
