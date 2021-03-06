/*******************************************************************************
 * Copyright 2012 Michael Mimo Moratti
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package ch.mimo.netty.handler.codec.icap;


/**
 * Main Icap Request implementation. This is the starting point to create a Icap request.
 * 
 * @author Michael Mimo Moratti (mimo@mimo.ch)
 *
 */
public class DefaultIcapRequest extends AbstractIcapMessage implements IcapRequest {
	
	private IcapMethod method;
	private String uri;
	
	/**
	 * This will create an initial icap request with all necessary details.
	 * 
	 * @param icapVersion the version of this request.
	 * @param method the method.
	 * @param uri the uri to reach with this request.
	 * @param host the host from where this request originates from. Because this is a mandatory Icap header
	 * you have to give a value and it will be directly added to the icap request as Host: header.
	 */
	public DefaultIcapRequest(IcapVersion icapVersion, IcapMethod method, String uri, String host) {
		super(icapVersion);
		this.method = method;
		this.uri = uri;
		addHeader(IcapHeaders.Names.HOST,host);
	}
	
	public IcapMessage setMethod(IcapMethod method) {
		this.method = method;
		return this;
	}

	public IcapMethod getMethod() {
		return method;
	}

	public IcapMessage setUri(String uri) {
		this.uri = uri;
		return this;
	}

	public String getUri() {
		return uri;
	}
}
