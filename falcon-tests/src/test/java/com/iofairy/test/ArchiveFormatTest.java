package com.iofairy.test;

import com.iofairy.falcon.zip.ArchiveFormat;
import com.iofairy.falcon.zip.ArchiveType;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author GG
 * @version 1.0
 * @date 2022/8/27 21:17
 */
public class ArchiveFormatTest {

    @Test
    public void testOf() {
        ArchiveFormat af01 = ArchiveFormat.of("Z");
        ArchiveFormat af02 = ArchiveFormat.of(".Z");
        ArchiveFormat af03 = ArchiveFormat.of("tar.Z");
        ArchiveFormat af04 = ArchiveFormat.of("tar.gZ");
        ArchiveFormat af05 = ArchiveFormat.of(".tar.gZ");
        ArchiveFormat af06 = ArchiveFormat.of(".csv");
        ArchiveFormat af07 = ArchiveFormat.of("TXT");
        ArchiveFormat af08 = ArchiveFormat.of("GZ");
        ArchiveFormat af09 = ArchiveFormat.of(null);
        ArchiveFormat af10 = ArchiveFormat.of("");
        ArchiveFormat af11 = ArchiveFormat.of("z");
        ArchiveFormat af12 = ArchiveFormat.of(".z");
        ArchiveFormat af13 = ArchiveFormat.of("zip");
        ArchiveFormat af14 = ArchiveFormat.of("tar.z");
        ArchiveFormat af15 = ArchiveFormat.of("TBZ2");
        ArchiveFormat af16 = ArchiveFormat.of("tgz");
        ArchiveFormat af17 = ArchiveFormat.of("tgz", true);
        ArchiveFormat af18 = ArchiveFormat.of("zip", true);


        assertEquals(ArchiveFormat.Z_COMPRESS, af01);
        assertEquals(ArchiveFormat.Z_COMPRESS, af02);
        assertEquals(ArchiveFormat.TAR_Z, af03);
        assertEquals(ArchiveFormat.TAR_GZ, af04);
        assertEquals(ArchiveFormat.TAR_GZ, af05);
        assertNull(af06);
        assertNull(af07);
        assertEquals(ArchiveFormat.GZIP, af08);
        assertNull(af09);
        assertNull(af10);
        assertEquals(ArchiveFormat.Z_PACK, af11);
        assertEquals(ArchiveFormat.Z_PACK, af12);
        assertEquals(ArchiveFormat.ZIP, af13);
        assertEquals(ArchiveFormat.TAR_Z, af14);
        assertEquals(ArchiveFormat.TBZ2, af15);
        assertEquals(ArchiveFormat.TGZ, af16);
        assertEquals(ArchiveFormat.TAR_GZ, af17);
        assertEquals(ArchiveFormat.ZIP, af18);
    }

    @Test
    public void testArchiveTypes() {
        EnumSet<ArchiveType> archiveTypes1 = ArchiveFormat.ZIP.archiveTypes;
        EnumSet<ArchiveType> archiveTypes2 = ArchiveFormat.ISO.archiveTypes;

        // System.out.println(archiveTypes1.contains(ArchiveType.MULTI_FUNCTION));
        // System.out.println(archiveTypes1.contains(ArchiveType.ARCHIVING_ONLY));
        // System.out.println(archiveTypes1.contains(ArchiveType.DISK_IMAGE));
        // System.out.println(archiveTypes2.contains(ArchiveType.MULTI_FUNCTION));
        // System.out.println(archiveTypes2.contains(ArchiveType.ARCHIVING_ONLY));
        // System.out.println(archiveTypes2.contains(ArchiveType.DISK_IMAGE));

        assertTrue(archiveTypes1.contains(ArchiveType.MULTI_FUNCTION));
        assertFalse(archiveTypes1.contains(ArchiveType.ARCHIVING_ONLY));
        assertFalse(archiveTypes1.contains(ArchiveType.DISK_IMAGE));
        assertFalse(archiveTypes2.contains(ArchiveType.MULTI_FUNCTION));
        assertTrue(archiveTypes2.contains(ArchiveType.ARCHIVING_ONLY));
        assertTrue(archiveTypes2.contains(ArchiveType.DISK_IMAGE));

    }

    @Test
    public void testIsMixed() {
        ArchiveFormat af01 = ArchiveFormat.of("Z");     // false
        ArchiveFormat af02 = ArchiveFormat.of(".Z");        // false
        ArchiveFormat af03 = ArchiveFormat.of("tar.Z");     // true
        ArchiveFormat af04 = ArchiveFormat.of("tar.gZ");        // true
        ArchiveFormat af05 = ArchiveFormat.of(".tar.gZ");       // true
        ArchiveFormat af06 = ArchiveFormat.of(".csv");      // false
        ArchiveFormat af07 = ArchiveFormat.of("TXT");       // false
        ArchiveFormat af08 = ArchiveFormat.of("GZ");        // false
        ArchiveFormat af09 = ArchiveFormat.of(null);        // false
        ArchiveFormat af10 = ArchiveFormat.of("");      // false
        ArchiveFormat af11 = ArchiveFormat.of("z");     // false
        ArchiveFormat af12 = ArchiveFormat.of(".z");        // false
        ArchiveFormat af13 = ArchiveFormat.of("zip");       // false
        ArchiveFormat af14 = ArchiveFormat.of("tar.z");     // true
        ArchiveFormat af15 = ArchiveFormat.of("TBZ2");      // true
        ArchiveFormat af16 = ArchiveFormat.of("tgz");       // true
        ArchiveFormat af17 = ArchiveFormat.of("tgz", true);     // true
        ArchiveFormat af18 = ArchiveFormat.of("zip", true);     // false

        boolean mixedFormat01 = ArchiveFormat.isMixedFormat(af01);
        boolean mixedFormat02 = ArchiveFormat.isMixedFormat(af02);
        boolean mixedFormat03 = ArchiveFormat.isMixedFormat(af03);
        boolean mixedFormat04 = ArchiveFormat.isMixedFormat(af04);
        boolean mixedFormat05 = ArchiveFormat.isMixedFormat(af05);
        boolean mixedFormat06 = ArchiveFormat.isMixedFormat(af06);
        boolean mixedFormat07 = ArchiveFormat.isMixedFormat(af07);
        boolean mixedFormat08 = ArchiveFormat.isMixedFormat(af08);
        boolean mixedFormat09 = ArchiveFormat.isMixedFormat(af09);
        boolean mixedFormat10 = ArchiveFormat.isMixedFormat(af10);
        boolean mixedFormat11 = af11.isMixedFormat();
        boolean mixedFormat12 = af12.isMixedFormat();
        boolean mixedFormat13 = af13.isMixedFormat();
        boolean mixedFormat14 = af14.isMixedFormat();
        boolean mixedFormat15 = af15.isMixedFormat();
        boolean mixedFormat16 = af16.isMixedFormat();
        boolean mixedFormat17 = af17.isMixedFormat();
        boolean mixedFormat18 = af18.isMixedFormat();

        assertFalse(mixedFormat01);
        assertFalse(mixedFormat02);
        assertTrue(mixedFormat03);
        assertTrue(mixedFormat04);
        assertTrue(mixedFormat05);
        assertFalse(mixedFormat06);
        assertFalse(mixedFormat07);
        assertFalse(mixedFormat08);
        assertFalse(mixedFormat09);
        assertFalse(mixedFormat10);
        assertFalse(mixedFormat11);
        assertFalse(mixedFormat12);
        assertFalse(mixedFormat13);
        assertTrue(mixedFormat14);
        assertTrue(mixedFormat15);
        assertTrue(mixedFormat16);
        assertTrue(mixedFormat17);
        assertFalse(mixedFormat18);
    }

    @Test
    public void testIsMultiExts() {
        ArchiveFormat af01 = ArchiveFormat.of("Z");         // false
        ArchiveFormat af02 = ArchiveFormat.of(".Z");            // false
        ArchiveFormat af03 = ArchiveFormat.of("tar.Z");         // true
        ArchiveFormat af04 = ArchiveFormat.of("tar.gZ");            // true
        ArchiveFormat af05 = ArchiveFormat.of(".tar.gZ");           // true
        ArchiveFormat af06 = ArchiveFormat.of(".csv");          // false
        ArchiveFormat af07 = ArchiveFormat.of("TXT");           // false
        ArchiveFormat af08 = ArchiveFormat.of("GZ");            // false
        ArchiveFormat af09 = ArchiveFormat.of(null);            // false
        ArchiveFormat af10 = ArchiveFormat.of("");          // false
        ArchiveFormat af11 = ArchiveFormat.of("z");         // false
        ArchiveFormat af12 = ArchiveFormat.of(".z");            // false
        ArchiveFormat af13 = ArchiveFormat.of("zip");           // false
        ArchiveFormat af14 = ArchiveFormat.of("tar.z");         // true
        ArchiveFormat af15 = ArchiveFormat.of("TBZ2");          // false
        ArchiveFormat af16 = ArchiveFormat.of("tgz");           // false
        ArchiveFormat af17 = ArchiveFormat.of("tgz", true);         // true
        ArchiveFormat af18 = ArchiveFormat.of("zip", true);         // false

        boolean multiExtsFormat01 = ArchiveFormat.isMultiExtsFormat(af01);
        boolean multiExtsFormat02 = ArchiveFormat.isMultiExtsFormat(af02);
        boolean multiExtsFormat03 = ArchiveFormat.isMultiExtsFormat(af03);
        boolean multiExtsFormat04 = ArchiveFormat.isMultiExtsFormat(af04);
        boolean multiExtsFormat05 = ArchiveFormat.isMultiExtsFormat(af05);
        boolean multiExtsFormat06 = ArchiveFormat.isMultiExtsFormat(af06);
        boolean multiExtsFormat07 = ArchiveFormat.isMultiExtsFormat(af07);
        boolean multiExtsFormat08 = ArchiveFormat.isMultiExtsFormat(af08);
        boolean multiExtsFormat09 = ArchiveFormat.isMultiExtsFormat(af09);
        boolean multiExtsFormat10 = ArchiveFormat.isMultiExtsFormat(af10);
        boolean multiExtsFormat11 = af11.isMultiExtsFormat();
        boolean multiExtsFormat12 = af12.isMultiExtsFormat();
        boolean multiExtsFormat13 = af13.isMultiExtsFormat();
        boolean multiExtsFormat14 = af14.isMultiExtsFormat();
        boolean multiExtsFormat15 = af15.isMultiExtsFormat();
        boolean multiExtsFormat16 = af16.isMultiExtsFormat();
        boolean multiExtsFormat17 = af17.isMultiExtsFormat();
        boolean multiExtsFormat18 = af18.isMultiExtsFormat();


        assertFalse(multiExtsFormat01);
        assertFalse(multiExtsFormat02);
        assertTrue(multiExtsFormat03);
        assertTrue(multiExtsFormat04);
        assertTrue(multiExtsFormat05);
        assertFalse(multiExtsFormat06);
        assertFalse(multiExtsFormat07);
        assertFalse(multiExtsFormat08);
        assertFalse(multiExtsFormat09);
        assertFalse(multiExtsFormat10);
        assertFalse(multiExtsFormat11);
        assertFalse(multiExtsFormat12);
        assertFalse(multiExtsFormat13);
        assertTrue(multiExtsFormat14);
        assertFalse(multiExtsFormat15);
        assertFalse(multiExtsFormat16);
        assertTrue(multiExtsFormat17);
        assertFalse(multiExtsFormat18);

    }
}
