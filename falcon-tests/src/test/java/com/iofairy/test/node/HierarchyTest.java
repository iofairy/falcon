package com.iofairy.test.node;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iofairy.falcon.node.HierarchyBuilder;
import com.iofairy.falcon.node.TenantTree;
import com.iofairy.falcon.time.DateTime;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author GG
 * @version 1.0
 * @date 2024/1/20 17:18
 */
public class HierarchyTest {

    static ObjectMapper mapper = new ObjectMapper();


    @SneakyThrows
    @Test
    public void testBuildTree() {
        DateTime<Date> dateTime = DateTime.parseDate("2024/01/01 10:35:30");

        DateTime<Date> dateTime01 = dateTime.minusHours(5);
        DateTime<Date> dateTime02 = dateTime.minusDays(1);
        DateTime<Date> dateTime03 = dateTime.minusSeconds(50);
        DateTime<Date> dateTime04 = dateTime.minusMinutes(-35);
        DateTime<Date> dateTime05 = dateTime.plusHours(5);
        DateTime<Date> dateTime06 = dateTime.plusDays(1);
        DateTime<Date> dateTime07 = dateTime.plusSeconds(50);
        Date dateTime08 = null;
        DateTime<Date> dateTime09 = dateTime.plusMinutes(-20);

        /*
          问题：
          数据的问题 加上 有多个顶级部门的父ID（0和null），导致部门深度混乱的问题？
          这里如果写个 部门0，且 顶级部门的父ID是 0和null，导致部门深度混乱的问题。比如：
          部门0的父ID是 null，所以部门0是顶级部门，那么 部门1 深度就是1，但是 部门1 的父ID是 0，
          所以部门1也是顶级部门，则 部门1 的深度是 0，与之前的深度是 1冲突。

          解决办法：
          1、顶级部门的父ID 不应该是一个真实的部门ID。也就是不应该有一条记录的部门ID与顶级部门的父ID一致。
          2、不应该存在两个相同的部门ID。
         */
        // Dept dept1 = new Dept(null, null, 0, "部门0", dateTime01.get());
        Dept dept2 = new Dept(null, 0, 1, "部门1", dateTime02.get());
        Dept dept3 = new Dept(null, null, 2, "部门2", dateTime03.get());
        Dept dept4 = new Dept("tenant1", null, 3, "部门3", dateTime04.get());
        Dept dept5 = new Dept("tenant1", 0, 4, "部门4", dateTime05.get());
        Dept dept6 = new Dept("tenant2", null, 5, "部门5", dateTime06.get());
        Dept dept7 = new Dept("tenant2", 0, 6, "部门6", dateTime07.get());
        Dept dept8 = new Dept("tenant2", 0, 7, "部门7", dateTime08);
        Dept dept10 = new Dept(null, 1, 10, "部门10", dateTime09.get());
        Dept dept11 = new Dept(null, 1, 11, "部门11", dateTime01.get());
        Dept dept12 = new Dept(null, 1, 12, "部门12", dateTime08);
        Dept dept13 = new Dept(null, 2, 13, "部门13", dateTime09.get());
        Dept dept14 = new Dept(null, 2, 14, "部门14", dateTime01.get());
        Dept dept15 = new Dept("tenant1", 3, 15, "部门15", dateTime02.get());
        Dept dept16 = new Dept("tenant1", 3, 16, "部门16", dateTime08);
        Dept dept17 = new Dept("tenant2", 5, 17, "部门17", dateTime03.get());
        Dept dept18 = new Dept("tenant2", 5, 18, "部门18", dateTime08);
        Dept dept19 = new Dept("tenant2", 6, 19, "部门19", dateTime04.get());
        Dept dept20 = new Dept("tenant2", 7, 20, "部门20", dateTime05.get());
        Dept dept21 = new Dept("tenant2", 7, 21, "部门21", dateTime06.get());
        Dept dept22 = new Dept("tenant2", 7, 22, "部门22", dateTime08);
        Dept dept112 = new Dept(null, 12, 112, "部门112", dateTime08);
        Dept dept113 = new Dept(null, 13, 113, "部门113", dateTime09.get());
        Dept dept116 = new Dept("tenant1", 16, 116, "部门116", dateTime08);
        Dept dept117 = new Dept("tenant2", 17, 117, "部门117", dateTime03.get());
        Dept dept1110 = new Dept(null, 113, 1110, "部门1110", dateTime09.get());

        List<Dept> depts = Arrays.asList(
                // dept1,
                dept2,
                dept3,
                dept4,
                dept5,
                dept6,
                dept7,
                dept8,
                dept10,
                dept11,
                dept12,
                dept13,
                dept14,
                dept15,
                dept16,
                dept17,
                dept18,
                dept19,
                dept20,
                dept21,
                dept22,
                dept112,
                dept113,
                dept116,
                dept117,
                dept1110);

        HierarchyBuilder<Dept, Integer> builder = HierarchyBuilder.on(depts);

        List<Dept> deptTree = builder.buildTree(Arrays.asList(0, null), true, true, true, null);
        String tree = mapper.writeValueAsString(deptTree);
        System.out.println(tree);
        System.out.println("============================================================");

        Comparator<Dept> deptCom = Comparator.comparing(Dept::getCreateTime, Comparator.nullsLast(Comparator.naturalOrder()));
        deptTree = builder.buildTree(Arrays.asList(0, null), true, true, true, deptCom);
        tree = mapper.writeValueAsString(deptTree);
        System.out.println(tree);
        System.out.println("============================================================");

        List<TenantTree<Dept>> tenantTrees = builder.buildTenantTree(Arrays.asList(0, null), true, true, true, null);
        String tenantTree = mapper.writeValueAsString(tenantTrees);
        System.out.println(tenantTree);
        System.out.println("============================================================");

        Comparator<Dept> deptCom1 = Comparator.comparing(Dept::getCreateTime, Comparator.nullsFirst(Comparator.naturalOrder()));
        tenantTrees = builder.buildTenantTree(Arrays.asList(0, null), true, true, true, deptCom1);
        tenantTree = mapper.writeValueAsString(tenantTrees);
        System.out.println(tenantTree);


    }

    @SneakyThrows
    @Test
    public void testBuildTree1() {
        DateTime<Date> dateTime = DateTime.parseDate("2024/01/01 10:35:30");

        DateTime<Date> dateTime01 = dateTime.minusHours(5);
        DateTime<Date> dateTime02 = dateTime.minusDays(1);
        DateTime<Date> dateTime03 = dateTime.minusSeconds(50);
        DateTime<Date> dateTime04 = dateTime.minusMinutes(-35);
        DateTime<Date> dateTime05 = dateTime.plusHours(5);
        DateTime<Date> dateTime06 = dateTime.plusDays(1);
        DateTime<Date> dateTime07 = dateTime.plusSeconds(50);
        Date dateTime08 = null;
        DateTime<Date> dateTime09 = dateTime.plusMinutes(-20);

        /*
          问题：
          数据的问题 加上 有多个顶级部门的父ID（0和null），导致部门深度混乱的问题？
          这里如果写个 部门0，且 顶级部门的父ID是 0和null，导致部门深度混乱的问题。比如：
          部门0的父ID是 null，所以部门0是顶级部门，那么 部门1 深度就是1，但是 部门1 的父ID是 0，
          所以部门1也是顶级部门，则 部门1 的深度是 0，与之前的深度是 1冲突。

          解决办法：
          1、顶级部门的父ID 不应该是一个真实的部门ID。也就是不应该有一条记录的部门ID与顶级部门的父ID一致。
          2、不应该存在两个相同的部门ID。
         */
        // Dept dept1 = new Dept(null, null, 0, "部门0", dateTime01.get());
        Dept1 dept2 = new Dept1(null, "0", "1", "部门1", dateTime02.get());
        Dept1 dept3 = new Dept1(null, null, "2", "部门2", dateTime03.get());
        Dept1 dept4 = new Dept1("tenant1", null, "3", "部门3", dateTime04.get());
        Dept1 dept5 = new Dept1("tenant1", "0", "4", "部门4", dateTime05.get());
        Dept1 dept6 = new Dept1("tenant2", null, "5", "部门5", dateTime06.get());
        Dept1 dept7 = new Dept1("tenant2", "0", "6", "部门6", dateTime07.get());
        Dept1 dept8 = new Dept1("tenant2", "0", "7", "部门7", dateTime08);
        Dept1 dept10 = new Dept1(null, "1", "10", "部门10", dateTime09.get());
        Dept1 dept11 = new Dept1(null, "1", "11", "部门11", dateTime01.get());
        Dept1 dept12 = new Dept1(null, "1", "12", "部门12", dateTime08);
        Dept1 dept13 = new Dept1(null, "2", "13", "部门13", dateTime09.get());
        Dept1 dept14 = new Dept1(null, "2", "14", "部门14", dateTime01.get());
        Dept1 dept15 = new Dept1("tenant1", "3", "15", "部门15", dateTime02.get());
        Dept1 dept16 = new Dept1("tenant1", "3", "16", "部门16", dateTime08);
        Dept1 dept17 = new Dept1("tenant2", "5", "17", "部门17", dateTime03.get());
        Dept1 dept18 = new Dept1("tenant2", "5", "18", "部门18", dateTime08);
        Dept1 dept19 = new Dept1("tenant2", "6", "19", "部门19", dateTime04.get());
        Dept1 dept20 = new Dept1("tenant2", "7", "20", "部门20", dateTime05.get());
        Dept1 dept21 = new Dept1("tenant2", "7", "21", "部门21", dateTime06.get());
        Dept1 dept22 = new Dept1("tenant2", "7", "22", "部门22", dateTime08);
        Dept1 dept112 = new Dept1(null, "12", "112", "部门112", dateTime08);
        Dept1 dept113 = new Dept1(null, "13", "113", "部门113", dateTime09.get());
        Dept1 dept116 = new Dept1("tenant1", "16", "116", "部门116", dateTime08);
        Dept1 dept117 = new Dept1("tenant2", "17", "117", "部门117", dateTime03.get());
        Dept1 dept1110 = new Dept1(null, "113", "1110", "部门1110", dateTime09.get());

        List<Dept1> depts = Arrays.asList(
                // dept1,
                dept2,
                dept3,
                dept4,
                dept5,
                dept6,
                dept7,
                dept8,
                dept10,
                dept11,
                dept12,
                dept13,
                dept14,
                dept15,
                dept16,
                dept17,
                dept18,
                dept19,
                dept20,
                dept21,
                dept22,
                dept112,
                dept113,
                dept116,
                dept117,
                dept1110);

        HierarchyBuilder<Dept1, String> builder = HierarchyBuilder.on(depts);

        List<Dept1> deptTree = builder.buildTree(Arrays.asList("0", null), true, true, true, null);
        String tree = mapper.writeValueAsString(deptTree);
        System.out.println(tree);
        System.out.println("============================================================");

        Comparator<Dept1> deptCom = Comparator.comparing(Dept1::getCreateTime, Comparator.nullsLast(Comparator.naturalOrder()));
        deptTree = builder.buildTree(Arrays.asList("0", null), true, true, true, deptCom);
        tree = mapper.writeValueAsString(deptTree);
        System.out.println(tree);
        System.out.println("============================================================");

        List<TenantTree<Dept1>> tenantTrees = builder.buildTenantTree(Arrays.asList("0", null), true, true, true, null);
        String tenantTree = mapper.writeValueAsString(tenantTrees);
        System.out.println(tenantTree);
        System.out.println("============================================================");

        Comparator<Dept1> deptCom1 = Comparator.comparing(Dept1::getCreateTime, Comparator.nullsFirst(Comparator.naturalOrder()));
        tenantTrees = builder.buildTenantTree(Arrays.asList("0", null), true, true, true, deptCom1);
        tenantTree = mapper.writeValueAsString(tenantTrees);
        System.out.println(tenantTree);


    }


    /**
     * 部门重复测试。
     * 测试结果：树中也会有多个重复的部门，且重复的部门下的子节点都是一致的
     */
    @SneakyThrows
    @Test
    public void testBuildTreeDuplicateDept() {
        DateTime<Date> dateTime = DateTime.parseDate("2024/01/01 10:35:30");

        DateTime<Date> dateTime01 = dateTime.minusHours(5);
        DateTime<Date> dateTime02 = dateTime.minusDays(1);
        DateTime<Date> dateTime03 = dateTime.minusSeconds(50);
        DateTime<Date> dateTime04 = dateTime.minusMinutes(-35);
        DateTime<Date> dateTime05 = dateTime.plusHours(5);
        DateTime<Date> dateTime06 = dateTime.plusDays(1);
        DateTime<Date> dateTime07 = dateTime.plusSeconds(50);
        Date dateTime08 = null;
        DateTime<Date> dateTime09 = dateTime.plusMinutes(-20);

        // Dept dept1 = new Dept(null, null, 0, "部门0", dateTime01.get());
        Dept dept2 = new Dept(null, 0, 1, "部门1", dateTime02.get());
        Dept dept3 = new Dept(null, null, 2, "部门2", dateTime03.get());
        Dept dept4 = new Dept("tenant1", null, 3, "部门3", dateTime04.get());
        Dept dept5 = new Dept("tenant1", 0, 4, "部门4", dateTime05.get());
        Dept dept6 = new Dept("tenant2", null, 5, "部门5", dateTime06.get());
        Dept dept7 = new Dept("tenant2", 0, 6, "部门6", dateTime07.get());
        Dept dept8 = new Dept("tenant2", 0, 7, "部门7", dateTime08);
        Dept dept10 = new Dept(null, 1, 10, "部门10", dateTime09.get());
        Dept dept11 = new Dept(null, 1, 11, "部门11", dateTime01.get());
        Dept dept12 = new Dept(null, 1, 12, "部门12", dateTime08);
        Dept dept12_dup = new Dept(null, 2, 12, "部门12_重复", dateTime08);
        Dept dept13 = new Dept(null, 2, 13, "部门13", dateTime09.get());
        Dept dept13_dup = new Dept(null, 2, 13, "部门13_重复", dateTime09.get());
        Dept dept14 = new Dept(null, 2, 14, "部门14", dateTime01.get());
        Dept dept15 = new Dept("tenant1", 3, 15, "部门15", dateTime02.get());
        Dept dept16 = new Dept("tenant1", 3, 16, "部门16", dateTime08);
        Dept dept17 = new Dept("tenant2", 5, 17, "部门17", dateTime03.get());
        Dept dept18 = new Dept("tenant2", 5, 18, "部门18", dateTime08);
        Dept dept19 = new Dept("tenant2", 6, 19, "部门19", dateTime04.get());
        Dept dept20 = new Dept("tenant2", 7, 20, "部门20", dateTime05.get());
        Dept dept21 = new Dept("tenant2", 7, 21, "部门21", dateTime06.get());
        Dept dept22 = new Dept("tenant2", 7, 22, "部门22", dateTime08);
        Dept dept112 = new Dept(null, 12, 112, "部门112", dateTime08);
        Dept dept113 = new Dept(null, 13, 113, "部门113", dateTime09.get());
        Dept dept116 = new Dept("tenant1", 16, 116, "部门116", dateTime08);
        Dept dept117 = new Dept("tenant2", 17, 117, "部门117", dateTime03.get());
        Dept dept1110 = new Dept(null, 113, 1110, "部门1110", dateTime09.get());

        List<Dept> depts = Arrays.asList(
                // dept1,
                dept2,
                dept3,
                dept4,
                dept5,
                dept6,
                dept7,
                dept8,
                dept10,
                dept11,
                dept12,
                dept12_dup,
                dept13,
                dept13_dup,
                dept14,
                dept15,
                dept16,
                dept17,
                dept18,
                dept19,
                dept20,
                dept21,
                dept22,
                dept112,
                dept113,
                dept116,
                dept117,
                dept1110);

        HierarchyBuilder<Dept, Integer> builder = HierarchyBuilder.on(depts);

        List<Dept> deptTree = builder.buildTree(Arrays.asList(0, null), true, true, null);
        String tree = mapper.writeValueAsString(deptTree);
        System.out.println(tree);
        System.out.println("============================================================");

        Comparator<Dept> deptCom = Comparator.comparing(Dept::getCreateTime, Comparator.nullsLast(Comparator.naturalOrder()));
        deptTree = builder.buildTree(Arrays.asList(0, null), true, true, deptCom);
        tree = mapper.writeValueAsString(deptTree);
        System.out.println(tree);
        System.out.println("============================================================");

        List<TenantTree<Dept>> tenantTrees = builder.buildTenantTree(Arrays.asList(0, null), true, true, null);
        String tenantTree = mapper.writeValueAsString(tenantTrees);
        System.out.println(tenantTree);
        System.out.println("============================================================");

        Comparator<Dept> deptCom1 = Comparator.comparing(Dept::getCreateTime, Comparator.nullsFirst(Comparator.naturalOrder()));
        tenantTrees = builder.buildTenantTree(Arrays.asList(0, null), true, true, deptCom1);
        tenantTree = mapper.writeValueAsString(tenantTrees);
        System.out.println(tenantTree);

    }


    /**
     * 不存在的父类部门测试
     * 测试结果：父部门不存在，则其子部门也不会出现在树中
     */
    @SneakyThrows
    @Test
    public void testBuildTreeParentIDNotExist() {
        DateTime<Date> dateTime = DateTime.parseDate("2024/01/01 10:35:30");

        DateTime<Date> dateTime01 = dateTime.minusHours(5);
        DateTime<Date> dateTime02 = dateTime.minusDays(1);
        DateTime<Date> dateTime03 = dateTime.minusSeconds(50);
        DateTime<Date> dateTime04 = dateTime.minusMinutes(-35);
        DateTime<Date> dateTime05 = dateTime.plusHours(5);
        DateTime<Date> dateTime06 = dateTime.plusDays(1);
        DateTime<Date> dateTime07 = dateTime.plusSeconds(50);
        Date dateTime08 = null;
        DateTime<Date> dateTime09 = dateTime.plusMinutes(-20);

        /*
          问题：
          数据的问题 加上 有多个顶级部门的父ID（0和null），导致部门深度混乱的问题？
          这里如果写个 部门0，且 顶级部门的父ID是 0和null，导致部门深度混乱的问题。比如：
          部门0的父ID是 null，所以部门0是顶级部门，那么 部门1 深度就是1，但是 部门1 的父ID是 0，
          所以部门1也是顶级部门，则 部门1 的深度是 0，与之前的深度是 1冲突。

          解决办法：
          1、顶级部门的父ID 不应该是一个真实的部门ID。也就是不应该有一条记录的部门ID与顶级部门的父ID一致。
          2、不应该存在两个相同的部门ID。
         */
        // Dept dept1 = new Dept(null, null, 0, "部门0", dateTime01.get());
        Dept dept2 = new Dept(null, 0, 1, "部门1", dateTime02.get());
        Dept dept3 = new Dept(null, null, 2, "部门2", dateTime03.get());
        Dept dept4 = new Dept("tenant1", null, 3, "部门3", dateTime04.get());
        Dept dept5 = new Dept("tenant1", 0, 4, "部门4", dateTime05.get());
        Dept dept6 = new Dept("tenant2", null, 5, "部门5", dateTime06.get());
        Dept dept7 = new Dept("tenant2", 0, 6, "部门6", dateTime07.get());
        Dept dept8 = new Dept("tenant2", 0, 7, "部门7", dateTime08);
        Dept dept10 = new Dept(null, 1, 10, "部门10", dateTime09.get());
        Dept dept11 = new Dept(null, 1, 11, "部门11", dateTime01.get());
        // Dept dept12 = new Dept(null, 1, 12, "部门12", dateTime08);
        Dept dept13 = new Dept(null, 2, 13, "部门13", dateTime09.get());
        Dept dept14 = new Dept(null, 2, 14, "部门14", dateTime01.get());
        Dept dept15 = new Dept("tenant1", 3, 15, "部门15", dateTime02.get());
        Dept dept16 = new Dept("tenant1", 3, 16, "部门16", dateTime08);
        Dept dept17 = new Dept("tenant2", 5, 17, "部门17", dateTime03.get());
        Dept dept18 = new Dept("tenant2", 5, 18, "部门18", dateTime08);
        Dept dept19 = new Dept("tenant2", 6, 19, "部门19", dateTime04.get());
        Dept dept20 = new Dept("tenant2", 7, 20, "部门20", dateTime05.get());
        Dept dept21 = new Dept("tenant2", 7, 21, "部门21", dateTime06.get());
        Dept dept22 = new Dept("tenant2", 7, 22, "部门22", dateTime08);
        Dept dept112 = new Dept(null, 12, 112, "部门112", dateTime08);
        Dept dept113 = new Dept(null, 13, 113, "部门113", dateTime09.get());
        Dept dept116 = new Dept("tenant1", 16, 116, "部门116", dateTime08);
        Dept dept117 = new Dept("tenant2", 17, 117, "部门117", dateTime03.get());
        Dept dept1110 = new Dept(null, 113, 1110, "部门1110", dateTime09.get());

        List<Dept> depts = Arrays.asList(
                // dept1,
                dept2,
                dept3,
                dept4,
                dept5,
                dept6,
                dept7,
                dept8,
                dept10,
                dept11,
                // dept12,
                dept13,
                dept14,
                dept15,
                dept16,
                dept17,
                dept18,
                dept19,
                dept20,
                dept21,
                dept22,
                dept112,
                dept113,
                dept116,
                dept117,
                dept1110);

        HierarchyBuilder<Dept, Integer> builder = HierarchyBuilder.on(depts);

        List<Dept> deptTree = builder.buildTree(Arrays.asList(0, null), true, true, null);
        String tree = mapper.writeValueAsString(deptTree);
        System.out.println(tree);
        System.out.println("============================================================");

        Comparator<Dept> deptCom = Comparator.comparing(Dept::getCreateTime, Comparator.nullsLast(Comparator.naturalOrder()));
        deptTree = builder.buildTree(Arrays.asList(0, null), true, true, deptCom);
        tree = mapper.writeValueAsString(deptTree);
        System.out.println(tree);
        System.out.println("============================================================");

        List<TenantTree<Dept>> tenantTrees = builder.buildTenantTree(Arrays.asList(0, null), true, true, null);
        String tenantTree = mapper.writeValueAsString(tenantTrees);
        System.out.println(tenantTree);
        System.out.println("============================================================");

        Comparator<Dept> deptCom1 = Comparator.comparing(Dept::getCreateTime, Comparator.nullsFirst(Comparator.naturalOrder()));
        tenantTrees = builder.buildTenantTree(Arrays.asList(0, null), true, true, deptCom1);
        tenantTree = mapper.writeValueAsString(tenantTrees);
        System.out.println(tenantTree);

    }

}
