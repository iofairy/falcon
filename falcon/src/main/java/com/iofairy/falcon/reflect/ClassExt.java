/*
 * Copyright (C) 2021 iofairy, <https://github.com/iofairy/falcon>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.iofairy.falcon.reflect;

import com.iofairy.falcon.os.OS;
import com.iofairy.top.G;
import com.iofairy.top.S;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.iofairy.falcon.os.JavaVersion.*;

/**
 * Class info extension <br>
 * Class字节码信息扩展
 *
 * @since 0.2.4
 */
public class ClassExt {
    /**
     * Class name. See: {@link Class#getName()}
     */
    public final String name;
    /**
     * Class simpleName. See: {@link Class#getSimpleName()}
     */
    public final String simpleName;
    /**
     * Class simpleName with {@link Class#getEnclosingClass()}'s simpleName
     */
    public final String simpleNameWithEnclosing;
    /**
     * Class typeName. See: {@link Class#getTypeName()}
     */
    public final String typeName;
    /**
     * Class canonicalName. See: {@link Class#getCanonicalName()}
     */
    public final String canonicalName;
    /**
     * Class Package. See: {@link Class#getPackage()}
     */
    public final Package pkg;
    /**
     * Class import clause.<br>
     * <b>Example: </b><br>
     * <pre>
     *     {@code import java.util.List;}
     * </pre>
     */
    public final String importClause;
    /**
     * Class static import clause.<br>
     * <b>Example: </b><br>
     * <pre>
     *     {@code import static com.iofairy.pattern.Pattern.*;}
     * </pre>
     */
    public final String importStaticClause;
    /**
     * Enclosing Classes
     */
    public final List<Class<?>> enclosingClasses;
    /**
     * Class modifiers. See: {@link Class#getModifiers()}
     */
    public final int modifiers;
    /**
     * The {@link Class} declared by the {@code class} keyword.
     */
    public final boolean isClass;
    /**
     * Class isMemberClass. See: {@link Class#isMemberClass()}
     */
    public final boolean isMemberClass;
    /**
     * Class isLocalClass. See: {@link Class#isLocalClass()}
     */
    public final boolean isLocalClass;
    /**
     * Class isAnonymousClass. See: {@link Class#isAnonymousClass()}
     */
    public final boolean isAnonymousClass;
    /**
     * Class isSynthetic. See: {@link Class#isSynthetic()}
     */
    public final boolean isSynthetic;
    /**
     * Class isHidden. See: {@code Class.isHidden()} since <b>Java 15</b>.
     */
    public final boolean isHidden;
    /**
     * Class isRecord. See: {@code Class.isRecord()} since <b>Java 16</b>.
     */
    public final boolean isRecord;
    /**
     * Class isSealed. See: {@code Class.isSealed()} since <b>Java 17</b>.
     */
    public final boolean isSealed;

    private ClassExt(String name, String simpleName, String simpleNameWithEnclosing, String typeName, String canonicalName, Package pkg, String importClause,
                     String importStaticClause, List<Class<?>> enclosingClasses, int modifiers, boolean isClass, boolean isMemberClass, boolean isLocalClass,
                     boolean isAnonymousClass, boolean isSynthetic, boolean isHidden, boolean isRecord, boolean isSealed) {
        this.name = name;
        this.simpleName = simpleName;
        this.simpleNameWithEnclosing = simpleNameWithEnclosing;
        this.typeName = typeName;
        this.canonicalName = canonicalName;
        this.pkg = pkg;
        this.importClause = importClause;
        this.importStaticClause = importStaticClause;
        this.enclosingClasses = enclosingClasses;
        this.modifiers = modifiers;
        this.isClass = isClass;
        this.isMemberClass = isMemberClass;
        this.isLocalClass = isLocalClass;
        this.isAnonymousClass = isAnonymousClass;
        this.isSynthetic = isSynthetic;
        this.isHidden = isHidden;
        this.isRecord = isRecord;
        this.isSealed = isSealed;
    }

    public static ClassExt of(Class<?> clazz) {
        if (clazz == null) return null;

        String simpleName = clazz.getSimpleName();
        /*
         * 获取所有外部类及带外部类的名称的 简单类名
         */
        Class<?> enclosingClass = clazz;
        List<Class<?>> enclosingClasses = new ArrayList<>();
        while ((enclosingClass = enclosingClass.getEnclosingClass()) != null) {
            enclosingClasses.add(enclosingClass);
        }
        Collections.reverse(enclosingClasses);
        String simpleNameWithEnclosing = G.isEmpty(enclosingClasses) ? simpleName : enclosingClasses.stream().map(Class::getSimpleName).collect(Collectors.joining(".")) + "." + simpleName;
        /*
         * 构造 import 语句
         */
        String canonicalName = clazz.getCanonicalName();
        String importClause = S.isEmpty(canonicalName) ? "" : "import " + canonicalName + ";";
        /*
         * 构造 静态 import 语句
         */
        String importStaticClause = S.isEmpty(canonicalName) ? "" : "import static " + canonicalName + ".*;";
        /*
         * 是否是类（class 关键字修饰）
         */
        boolean isClass = !clazz.isEnum() && !clazz.isInterface() && !clazz.isArray() && !clazz.isAnnotation() && !clazz.isPrimitive()/* && !isRecord*/;
        boolean isHidden = false;
        boolean isRecord = false;
        boolean isSealed = false;
        if (OS.J_VERSION != null) {
            if (OS.J_VERSION.gte(JAVA_15)) {
                Class<? extends Class> clazzClass = clazz.getClass();
                isHidden = callMethod(clazz, clazzClass, "isHidden");

                if (OS.J_VERSION.gte(JAVA_16)) {
                    isRecord = callMethod(clazz, clazzClass, "isRecord");
                    isClass = isClass && !isRecord;
                }

                if (OS.J_VERSION.gte(JAVA_17)) {
                    isSealed = callMethod(clazz, clazzClass, "isSealed");
                }
            }
        }

        return new ClassExt(clazz.getName(), simpleName, simpleNameWithEnclosing, clazz.getTypeName(), canonicalName, clazz.getPackage(),
                importClause, importStaticClause, enclosingClasses, clazz.getModifiers(), isClass, clazz.isMemberClass(), clazz.isLocalClass(),
                clazz.isAnonymousClass(), clazz.isSynthetic(), isHidden, isRecord, isSealed);
    }

    private static boolean callMethod(Class<?> clazz, Class<? extends Class> clazzClass, String methodName) {
        boolean isTrue = false;
        try {
            Method method = clazzClass.getMethod(methodName);
            isTrue = (boolean) method.invoke(clazz);
        } catch (Exception e) {
            String stackTrace = G.stackTrace(e);
        }
        return isTrue;
    }

    @Override
    public String toString() {
        return "ClassExt{" +
                "name='" + name + '\'' +
                ", simpleName='" + simpleName + '\'' +
                ", simpleNameWithEnclosing='" + simpleNameWithEnclosing + '\'' +
                ", typeName='" + typeName + '\'' +
                ", canonicalName='" + canonicalName + '\'' +
                ", pkg=" + pkg +
                ", importClause='" + importClause + '\'' +
                ", importStaticClause='" + importStaticClause + '\'' +
                ", enclosingClasses=" + enclosingClasses +
                ", modifiers=" + modifiers +
                ", isClass=" + isClass +
                ", isMemberClass=" + isMemberClass +
                ", isLocalClass=" + isLocalClass +
                ", isAnonymousClass=" + isAnonymousClass +
                ", isSynthetic=" + isSynthetic +
                ", isHidden=" + isHidden +
                ", isRecord=" + isRecord +
                ", isSealed=" + isSealed +
                '}';
    }
}
