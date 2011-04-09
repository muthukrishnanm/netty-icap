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
package ch.mimo.netty.handler.codec.icap.socket;

import java.util.concurrent.Executor;

import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.junit.Test;

import ch.mimo.netty.handler.codec.icap.DataMockery;
import ch.mimo.netty.handler.codec.icap.IcapRequest;

public class SimpleNioNioSocketTest extends AbstractSocketTest {

	@Override
	protected ChannelFactory newServerSocketChannelFactory(Executor executor) {
		return new NioServerSocketChannelFactory(executor, executor);
	}

	@Override
	protected ChannelFactory newClientSocketChannelFactory(Executor executor) {
		 return new NioClientSocketChannelFactory(executor, executor);
	}
	
	@Test
	public void sendOPTIONSRequest() {
		runDecoderTest(new ServerHandler(),new ClientHandler(),DataMockery.createOPTIONSIcapRequest());
	}
	
	private class ServerHandler extends AbstractServerHandler {

		@Override
		public void doMessageReceived(ChannelHandlerContext context, MessageEvent event) {
			IcapRequest request = (IcapRequest)event.getMessage();
			DataMockery.assertCreateOPTIONSRequest(request);
//			ChannelFuture channelFuture = event.getChannel().write(DataMockery.createOPTIONSIcapResponse());
//			channelFuture.addListener(ChannelFutureListener.CLOSE);
		}	
	}
	
	private class ClientHandler extends AbstractClientHandler {

		@Override
		public void doMessageReceived(ChannelHandlerContext context, ChannelEvent event) throws Exception {
//			IcapResponse response = (IcapResponse)event.getMessage();
//			System.out.println(response.toString());
		}

	}
}

