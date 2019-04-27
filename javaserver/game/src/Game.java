/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import msghandler.MsgHandlerRegistryFactory;
import network.AChannelManager;
import network.Acceptor;

public final class Game {

    private static volatile boolean isRun = true;

    public static void main(String[] args) throws Exception {

        MsgHandlerRegistryFactory.INSTANCE.get();

        EventLoopGroup bossGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors());
        EventLoopGroup workerGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors());

        AChannelManager aChannelManager = new ClientChannelManager();
        Acceptor clientAcceptor = new Acceptor("0.0.0.0", 8007, aChannelManager, bossGroup, workerGroup);
        clientAcceptor.start();

        while (isRun){
            Thread.sleep(1000000);
        }

        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}
