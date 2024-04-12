package makamys.neodymium.util;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import net.xiaoyu233.fml.relaunch.Launch;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

//import net.minecraft.launchwrapper.Launch;

public class Util {
    
    private static boolean allowResourceOverrides = Boolean.parseBoolean(System.getProperty("neodymium.allowResourceOverrides", "false"));
    
    public static Path getResourcePath(String relPath) {
        if(allowResourceOverrides) {
            // Launch.minecraftHome
            File overrideFile = new File(new File(Launch.minecraftHome, "neodymium/resources"), relPath);
            if(overrideFile.exists()) {
                return overrideFile.toPath();
            }
        }
        
        try {
            URL resourceURL = Util.class.getClassLoader().getResource(relPath);
            
            switch(resourceURL.getProtocol()) {
            case "jar":
                String urlString = resourceURL.getPath();
                int lastExclamation = urlString.lastIndexOf('!');
                String newURLString = urlString.substring(0, lastExclamation);
                return FileSystems.newFileSystem(new File(URI.create(newURLString)).toPath(), (ClassLoader) null).getPath(relPath);
            case "file":
                return new File(URI.create(resourceURL.toString())).toPath();
            default:
                return null;
            }
        } catch(IOException e) {
            return null;
        }
    }
    
    public static String readFile(String path){
        try {
            return new String(Files.readAllBytes(Util.getResourcePath(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
    
    public static byte[] byteBufferToArray(ByteBuffer buffer) {
        byte[] dst = new byte[buffer.limit()];
        int pos = buffer.position();
        buffer.position(0);
        buffer.get(dst);
        buffer.position(pos);
        return dst;
    }
    
    public static int[] intBufferToArray(IntBuffer buffer) {
        int[] dst = new int[buffer.limit()];
        int pos = buffer.position();
        buffer.position(0);
        buffer.get(dst);
        buffer.position(pos);
        return dst;
    }
    
    public static float[] floatBufferToArray(FloatBuffer buffer) {
        float[] dst = new float[buffer.limit()];
        int pos = buffer.position();
        buffer.position(0);
        buffer.get(dst);
        buffer.position(pos);
        return dst;
    }
    
    public static double distSq(double x1, double y1, double z1, double x2, double y2, double z2) {
        return Math.pow(x1 - x2, 2) +
                Math.pow(y1 - y2, 2) +
                Math.pow(z1 - z2, 2);
    }
    
    public static void dumpTexture() {
        int width = GL11.glGetTexLevelParameteri(GL11.GL_TEXTURE_2D, 0, GL11.GL_TEXTURE_WIDTH);
        int height = GL11.glGetTexLevelParameteri(GL11.GL_TEXTURE_2D, 0, GL11.GL_TEXTURE_HEIGHT);
        
        System.out.println("Dumped " + width + "x" + height + " texture.");
        
        ByteBuffer buf = BufferUtils.createByteBuffer(4 * width * height);
        GL11.glGetTexImage(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buf);
        /*try {
            // to convert to png:
            // magick -size 512x256 -depth 8 out.rgba out.png
            FileUtils.writeByteArrayToFile(new File("out.rgba"), Util.byteBufferToArray(buf));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        System.out.println("Dump not implemented");
    }
    
    public static int createBrightness(int sky, int block) {
        return sky << 20 | block << 4;
    }
}
