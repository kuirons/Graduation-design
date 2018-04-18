package com.bigao.backend.module.activeCode;

import com.google.common.base.Preconditions;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledByteBufAllocator;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

/**
 * 通用的激活码结构(6字节) -> 8字节
 * Created by shell on 2015/10/8.
 */
public class ActivationCode {
    private byte platform; // 指定平台生效,-1表示无限制
    private short reward; // 奖励id,同样也用来当做批次id
    private short id; // 该批次唯一id,即每批次最多生成32767个激活码
    private byte cover; // 1 对于相同reward的激活码，同一个玩家仅仅能够使用一次 2可多次使用不同id的激活码

    /**
     * 平台,服务器限制的激活码(9字节) -> 12字节
     */
    public static class ActivationCodeWithServer extends ActivationCode {
        private short server; // 指定服务器生效,-1表示无限制
        private byte sCover; // 为了补位到15

        public byte getsCover() {
            return sCover;
        }

        public void setsCover(byte sCover) {
            this.sCover = sCover;
        }

        public short getServer() {
            return server;
        }

        public void setServer(short server) {
            this.server = server;
        }

        @Override
        protected void write(ByteBuf buf) {
            super.write(buf);
            buf.writeShort(server);
            buf.writeByte(sCover);
        }

        @Override
        protected void read(ByteBuf buf) {
            super.read(buf);
            server = buf.readShort();
            sCover = buf.readByte();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;

            ActivationCodeWithServer that = (ActivationCodeWithServer) o;

            if (server != that.server) return false;
            return sCover == that.sCover;

        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + (int) server;
            result = 31 * result + (int) sCover;
            return result;
        }

        @Override
        public String toString() {
            return super.toString() + "  ActivationCodeWithPlatformServer{" +
                    "server=" + server +
                    ", sCover=" + sCover +
                    '}';
        }
    }

    public byte getCover() {
        return cover;
    }

    public void setCover(byte cover) {
        this.cover = cover;
    }

    public short getReward() {
        return reward;
    }

    public void setReward(short reward) {
        this.reward = reward;
    }

    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    public byte getPlatform() {
        return platform;
    }

    public void setPlatform(byte platform) {
        this.platform = platform;
    }

    private final static int CHECK_BYTES = 3;
    public String encrypt() throws UnsupportedEncodingException {
        ByteBuf buf = UnpooledByteBufAllocator.DEFAULT.heapBuffer();
        byte[] bytes;
        try {
            write(buf);
            bytes = new byte[buf.readableBytes() + CHECK_BYTES];
            buf.readBytes(bytes, 0, bytes.length - CHECK_BYTES);
        } finally {
            buf.release();
        }
        for (int i = 0; i < bytes.length - CHECK_BYTES; ++i) {
            int index = bytes.length - 1 - i % CHECK_BYTES;
            bytes[index] ^= bytes[i];
        }
        for (int i = 0; i < bytes.length - CHECK_BYTES; ++i) {
            int index = bytes.length - 1 - i % CHECK_BYTES;
            bytes[i] ^= bytes[index];
        }
        return new String(Base64.getUrlEncoder().encode(bytes), "UTF-8");
    }

    protected void write(ByteBuf buf) {
        buf.writeShort(reward);
        buf.writeShort(id);
        buf.writeByte(platform);
        buf.writeByte(cover);
    }

    public void decrypt(String str) throws UnsupportedEncodingException, NoSuchMethodException {
        byte[] bytes;
        bytes = Base64.getUrlDecoder().decode(str.getBytes("UTF-8"));
        for (int i = 0; i < bytes.length - CHECK_BYTES; ++i) {
            int index = bytes.length - 1 - i % CHECK_BYTES;
            bytes[i] ^= bytes[index];
        }
        byte[] check = new byte[CHECK_BYTES];
        for (int i = 0; i < bytes.length - CHECK_BYTES; ++i) {
            int index = i % CHECK_BYTES;
            check[index] ^= bytes[i];
        }
        for (int i = 0; i < CHECK_BYTES; ++i) {
            Preconditions.checkArgument(check[i] == bytes[bytes.length - 1 - i]);
        }
        ByteBuf buf = UnpooledByteBufAllocator.DEFAULT.heapBuffer();
        buf.writeBytes(bytes, 0, bytes.length - 3);
        try {
            read(buf);
        } finally {
            buf.release();
        }
    }

    protected void read(ByteBuf buf) {
        reward = buf.readShort();
        id = buf.readShort();
        platform = buf.readByte();
        cover = buf.readByte();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActivationCode that = (ActivationCode) o;

        if (platform != that.platform) return false;
        if (reward != that.reward) return false;
        if (id != that.id) return false;
        return cover == that.cover;

    }

    @Override
    public int hashCode() {
        int result = (int) platform;
        result = 31 * result + reward;
        result = 31 * result + (int) id;
        result = 31 * result + (int) cover;
        return result;
    }

    @Override
    public String toString() {
        return "ActivationCode{" +
                "platform=" + platform +
                ", reward=" + reward +
                ", id=" + id +
                ", cover=" + cover +
                '}';
    }

    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchMethodException {
//        CRC32 crc32 = new CRC32();
//        crc32.update("1234567890".getBytes());
//        System.err.println(crc32.getValue());
//        byte[] bytes = Base64.getEncoder().encode(new String("123456789012").getBytes());
//        System.err.println(new String(bytes));
//        if (true) {
//            return;
//        }

        System.out.println(Short.MAX_VALUE);
//        short ir = (short) CommonUtil.random(Integer.MIN_VALUE, Integer.MAX_VALUE-1);
//        int now = (int) (GameClock.millis() / 1000);
//        byte br = (byte) CommonUtil.random(Byte.MIN_VALUE, Byte.MAX_VALUE);
        byte br = (byte) -1;
//        short sr = (short) CommonUtil.random(Short.MIN_VALUE, Short.MAX_VALUE);
        byte sr = (byte) -1;

        int count = 32767;
//        int count = 10;
        /**
         * 12位激活码
         */
        System.err.println("===============================================================================================================");
        System.err.println("8位激活码");
        System.err.println("===============================================================================================================");
        Set<String> set12 = new HashSet<>();
        for (short i = 0; i < count; ++i) {
            ActivationCode c = new ActivationCode();
            c.setId(i);
            c.setReward((short) 8);
            c.setPlatform(br);

            String s = c.encrypt();
            Preconditions.checkArgument(!set12.contains(s));
            set12.add(s);
            ActivationCode tmp = new ActivationCode();
            tmp.decrypt(s);
            Preconditions.checkArgument(tmp.equals(c));
            System.err.println(s);
        }

        /**
         * 20位激活码
         */
        System.err.println("===============================================================================================================");
        System.err.println("12位激活码");
        System.err.println("===============================================================================================================");
        Set<String> set20 = new HashSet<>();
        for (short i = 0; i < count; ++i) {
            ActivationCodeWithServer c = new ActivationCodeWithServer();
            c.setId(i);
            c.setReward((short) 9);
            c.setPlatform(br);
            c.setServer(sr);

            String s = c.encrypt();
            Preconditions.checkArgument(!set20.contains(s));
            set20.add(s);
            ActivationCodeWithServer tmp = new ActivationCodeWithServer();
            tmp.decrypt(s);
            Preconditions.checkArgument(tmp.equals(c));
            System.err.println(s);
        }
    }
}
