package org.android.developer.util;

import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiPackage;

/**
 * @author LC
 * @des
 */
public class CommonUtil {

    /**
     * 获取一个类的包信息
     * @param psiClass
     * @param project
     * @return
     */
    public static PsiPackage getPkg(PsiClass psiClass, Project project) {
        PsiJavaFile javaFile = (PsiJavaFile) psiClass.getContainingFile();
        PsiPackage pkg = JavaPsiFacade.getInstance(project).findPackage(javaFile.getPackageName());
        return pkg;
    }
}
