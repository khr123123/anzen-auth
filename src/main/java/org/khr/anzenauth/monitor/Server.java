package org.khr.anzenauth.monitor;

import lombok.Data;
import org.khr.anzenauth.base.utils.Arith;
import org.khr.anzenauth.base.utils.IpUtils;
import org.khr.anzenauth.monitor.server.*;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 服务器相关信息采集器
 * 基于 OSHI 获取 CPU、内存、JVM、系统、磁盘信息
 *
 * @author kk
 */
@Data
public class Server {

    /**
     * CPU 信息
     */
    private Cpu cpu = new Cpu();

    /**
     * 内存信息
     */
    private Mem mem = new Mem();

    /**
     * JVM 信息
     */
    private Jvm jvm = new Jvm();

    /**
     * 系统信息
     */
    private Sys sys = new Sys();

    /**
     * 磁盘文件系统信息
     */
    private List<SysFile> sysFiles = new ArrayList<>();


    /**
     * 采集并填充所有信息
     */
    public void copyTo() {
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        OperatingSystem os = si.getOperatingSystem();
        Properties props = System.getProperties();

        setCpuInfo(hal.getProcessor());
        setMemInfo(hal.getMemory());
        setSysInfo(props);
        setJvmInfo(props);
        setSysFiles(os);
    }

    /**
     * 设置 CPU 信息
     */
    private void setCpuInfo(CentralProcessor processor) {
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
        long[] ticks = processor.getSystemCpuLoadTicks();

        long nice =
            ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
        long irq =
            ticks[CentralProcessor.TickType.IRQ.getIndex()] - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
        long softirq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()]
            - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
        long steal =
            ticks[CentralProcessor.TickType.STEAL.getIndex()] - prevTicks[CentralProcessor.TickType.STEAL.getIndex()];
        long cSys =
            ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
        long user =
            ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
        long iowait =
            ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
        long idle =
            ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];

        long totalCpu = user + nice + cSys + idle + iowait + irq + softirq + steal;

        cpu.setCpuNum(processor.getLogicalProcessorCount());
        cpu.setTotal(totalCpu);
        cpu.setSys(cSys);
        cpu.setUsed(user);
        cpu.setWait(iowait);
        cpu.setFree(idle);
    }


    /**
     * 设置内存信息
     */
    private void setMemInfo(GlobalMemory memory) {
        mem.setTotal(memory.getTotal());
        mem.setUsed(memory.getTotal() - memory.getAvailable());
        mem.setFree(memory.getAvailable());
    }

    /**
     * 设置系统信息
     */
    private void setSysInfo(Properties props) {
        sys.setComputerName(IpUtils.getHostName());
        sys.setComputerIp(IpUtils.getHostIp());
        sys.setOsName(props.getProperty("os.name"));
        sys.setOsArch(props.getProperty("os.arch"));
        sys.setUserDir(props.getProperty("user.dir"));
    }

    /**
     * 设置 JVM 信息
     */
    private void setJvmInfo(Properties props) {
        Runtime runtime = Runtime.getRuntime();
        jvm.setTotal(runtime.totalMemory());
        jvm.setMax(runtime.maxMemory());
        jvm.setFree(runtime.freeMemory());
        jvm.setVersion(props.getProperty("java.version"));
        jvm.setHome(props.getProperty("java.home"));
    }

    /**
     * 设置磁盘信息
     */
    private void setSysFiles(OperatingSystem os) {
        for (OSFileStore fs : os.getFileSystem().getFileStores()) {
            long free = fs.getUsableSpace();
            long total = fs.getTotalSpace();
            long used = total - free;

            SysFile sysFile = new SysFile();
            sysFile.setDirName(fs.getMount());
            sysFile.setSysTypeName(fs.getType());
            sysFile.setTypeName(fs.getName());
            sysFile.setTotal(formatSize(total));
            sysFile.setFree(formatSize(free));
            sysFile.setUsed(formatSize(used));
            sysFile.setUsage(Arith.mul(Arith.div(used, total, 4), 100));

            sysFiles.add(sysFile);
        }
    }

    /**
     * 格式化文件大小
     */
    private String formatSize(long size) {
        final long kb = 1024;
        final long mb = kb * 1024;
        final long gb = mb * 1024;
        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else {
            return String.format("%d B", size);
        }
    }
}
