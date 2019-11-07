package org.android.developer.util;

import com.intellij.codeInsight.completion.AllClassesGetter;
import com.intellij.codeInsight.completion.PlainPrefixMatcher;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.searches.ReferencesSearch;
import com.intellij.refactoring.util.MoveRenameUsageInfo;
import com.intellij.usageView.UsageInfo;
import com.intellij.util.Processor;
import com.intellij.util.Query;
import org.android.developer.visitor.MyPsiRecursiveElementVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LC
 * @des
 * @Reference https://intellij-support.jetbrains.com/hc/en-us/community/posts/206754855-ReferencesSearch-findAll-returns-Empty-Collection
 */
public class ParseAllFile {
    /**
     * 遍历工程，获取各个类文件
     * @param project
     */
    public static void parsePrj(Project project) {
        Processor<PsiClass> processor = new Processor<PsiClass>() {
            @Override
            public boolean process(PsiClass psiClass) {
                parseFile(psiClass);
                return true;
            }
        };

        AllClassesGetter.processJavaClasses(
                new PlainPrefixMatcher(""),
                project,
                GlobalSearchScope.projectScope(project),
                processor
        );
    }


    private static void parseFile(PsiClass psiClass) {
        LogUtil.log("parse->" + psiClass.getName());
    }

    /**
     * 获取指定文件的成员变量。
     * @param psiFile
     * @return
     */
    public static List<PsiField> gerVariablesList(PsiFile psiFile) {
        MyPsiRecursiveElementVisitor visitor = new MyPsiRecursiveElementVisitor();
        psiFile.accept(visitor);
        return visitor.getPsiFields();
    }

    /**
     * 获取引用
     * @param element
     * @param ctx
     * @return
     */
    public static List<UsageInfo> findUsages(PsiElement element, PsiElement ctx) {
//    Module mod = ModuleUtilCore.findModuleForPsiElement( element );
//    if( mod == null )
//    {
//      return Collections.emptyList();
//    }

        Query<PsiReference> search = ReferencesSearch.search(element, GlobalSearchScope.moduleScope(ModuleUtilCore.findModuleForPsiElement(ctx)));
        List<UsageInfo> usages = new ArrayList<>();
        for (PsiReference ref : search.findAll()) {
            MoveRenameUsageInfo usageInfo = new MoveRenameUsageInfo(ref.getElement(), ref, ref.getRangeInElement().getStartOffset(),
                    ref.getRangeInElement().getEndOffset(), element,
                    ref.resolve() == null && !(ref instanceof PsiPolyVariantReference && ((PsiPolyVariantReference) ref).multiResolve(true).length > 0));
            usages.add(usageInfo);
        }
        return usages;
    }
}
