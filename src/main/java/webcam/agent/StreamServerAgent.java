package webcam.agent;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.net.SocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webcam.channel.StreamServerChannelPipelineFactory;
import webcam.handler.H264StreamEncoder;
import webcam.handler.StreamServerListener;

import com.github.sarxos.webcam.Webcam;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class StreamServerAgent implements IStreamServerAgent {

    protected final static Logger logger = LoggerFactory.getLogger(StreamServerAgent.class);
    protected final Webcam webcam;
    protected final Dimension dimension;
    protected final ChannelGroup channelGroup = new DefaultChannelGroup();
    protected final ServerBootstrap serverBootstrap;
    //I just move the stream encoder out of the channel pipeline for the performance
    protected final H264StreamEncoder h264StreamEncoder;
    protected volatile boolean isStreaming;
    protected ScheduledExecutorService timeWorker;
    protected ExecutorService encodeWorker;
    protected int FPS = 25;
    protected ScheduledFuture<?> imageGrabTaskFuture;

    public StreamServerAgent(Webcam webcam, Dimension dimension) {
        super();
        this.webcam = webcam;
        this.dimension = dimension;
        //this.h264StreamEncoder = new H264StreamEncoder(dimension,false);
        this.serverBootstrap = new ServerBootstrap();
        this.serverBootstrap.setFactory(new NioServerSocketChannelFactory(
                Executors.newCachedThreadPool(),
                Executors.newCachedThreadPool()));
        this.serverBootstrap.setPipelineFactory(new StreamServerChannelPipelineFactory(
                new StreamServerListenerIMPL(),
                dimension));
        this.timeWorker = new ScheduledThreadPoolExecutor(1);
        this.encodeWorker = Executors.newSingleThreadExecutor();
        this.h264StreamEncoder = new H264StreamEncoder(dimension, false);
    }
    boolean flip = true;

    public void flip_flop_cam_horiz_write() {
        flip = !flip;
        try (PrintWriter iro = new PrintWriter(new File("camera_horiz.txt"))) {
            iro.println(flip);
            iro.flush();
            iro.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getFPS() {
        return FPS;
    }

    public void setFPS(int fPS) {
        FPS = fPS;
    }
    //StreamServerListenerIMPL.
    public ArrayList<String> dc_ips = new ArrayList<>();

    @Override
    public void start(SocketAddress streamAddress) {
        logger.info("Server started :{}", streamAddress);
        //Channel channel = serverBootstrap.bind(streamAddress);
        channelGroup.add(serverBootstrap.bind(streamAddress));
    }

    Boolean getcam = true;

    public void vait() {
        getcam = false;
        try {
            wait(100);
        } catch (Exception e) {
        }
        logger.info("server is stoping");
        channelGroup.close();
    }

    @Override
    public void stop() {
        getcam = false;
        try {
            wait(100);
        } catch (Exception e) {
        }
        logger.info("server is stoping");
        channelGroup.close();
        timeWorker.shutdown();
        encodeWorker.shutdown();
        serverBootstrap.releaseExternalResources();
    }

    private class StreamServerListenerIMPL implements StreamServerListener {

        @Override
        public void onClientConnectedIn(Channel channel) {
            //here we just start to stream when the first client connected in
            //
            flip_flop_cam_horiz_read();
            channelGroup.add(channel);
            if (!isStreaming) {
                //do some thing
                Runnable imageGrabTask = new ImageGrabTask();
                ScheduledFuture<?> imageGrabFuture
                        = timeWorker.scheduleWithFixedDelay(imageGrabTask,
                                0,
                                1000 / FPS,
                                TimeUnit.MILLISECONDS);
                imageGrabTaskFuture = imageGrabFuture;
                isStreaming = true;
            }
            logger.info("Client connected :{}", channel.getRemoteAddress());
        }

        @Override
        public void onClientDisconnected(Channel channel) {
            channelGroup.remove(channel);
            int size = channelGroup.size();
            logger.info("current connected clients :{}", size);
            if (size == 1) {
                //cancel the task
                imageGrabTaskFuture.cancel(false);
                //webcam.close();
                isStreaming = false;
            }
            logger.info("Client disconnected :{}", channel.getRemoteAddress());
            dc_ips.add(channel.getRemoteAddress().toString());
        }

        @Override
        public void onExcaption(Channel channel, Throwable t) {
            channelGroup.remove(channel);
            channel.close();
            int size = channelGroup.size();
            logger.info("current connected clients :{}", size);
            if (size == 1) {
                //cancel the task
                imageGrabTaskFuture.cancel(false);
                //webcam.close();
                isStreaming = false;

            }

        }

        protected volatile long frameCount = 0;

        class msg {

        }

        private class ImageGrabTask implements Runnable {

            @Override
            public void run() {

                //logger.info("image grabed ,count :{}",frameCount++);
                BufferedImage bufferedImage = null;
                if (getcam) {
                    bufferedImage = getcam();
                }
                /**
                 * using this when the h264 encoder is added to the pipeline
                 *
                 */
                //channelGroup.write(bufferedImage);
                /**
                 * using this when the h264 encoder is inside this class
                 *
                 */
                encodeWorker.execute(
                        new EncodeTask(bufferedImage));
            }

        }

        BufferedImage last;
        Boolean ip = false;
        URL ip_addres;

        public BufferedImage getcam() {
            if (ip) {
                try {
                    last = ImageIO.read(ip_addres);
                } catch (Exception e) {
                }
            } else if (flip) {
                last = flipHoriz(webcam.getImage());
            } else {
                last = webcam.getImage();
            }
            return last;
        }

        BufferedImage flipHoriz(BufferedImage image) {
            BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D gg = newImage.createGraphics();
            gg.drawImage(image, 0 + image.getWidth(), 0, -image.getWidth(), image.getHeight(), null);
            gg.dispose();
            //logger.info(newImage.getHeight() + "  " + newImage.getWidth());
            return newImage;
        }

        void flip_flop_cam_horiz_read() {
            try {
                Scanner in = new Scanner(new FileReader("camera_horiz.txt"));
                int a = 1;
                while (in.hasNext()) {
                    String kecske = in.nextLine();
                    if (!kecske.isEmpty()) {
                        System.out.println("txt tartalom: " + kecske);
                        flip = kecske.equals("true");
                        a++;
                    }
                }
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("not configured");
            }
        }

        private class EncodeTask implements Runnable {

            private final BufferedImage image;

            public EncodeTask(BufferedImage image) {
                super();
                this.image = image;
            }

            @Override
            public void run() {
                try {
                    Object msg = h264StreamEncoder.encode(image);
                    //msg+=
                    if (msg != null) {
                        channelGroup.write(msg);
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }

    }

}
