package org.android.developer.util;

import com.intellij.codeInsight.completion.AllClassesGetter;
import com.intellij.codeInsight.completion.PlainPrefixMatcher;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.searches.ReferencesSearch;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.refactoring.util.MoveRenameUsageInfo;
import com.intellij.usageView.UsageInfo;
import com.intellij.util.Processor;
import com.intellij.util.Query;
import org.android.developer.visitor.MyPsiRecursiveElementVisitor;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @author LC
 * @des
 * @Reference https://intellij-support.jetbrains.com/hc/en-us/community/posts/206754855-ReferencesSearch-findAll-returns-Empty-Collection
 * @Reference https://intellij-support.jetbrains.com/hc/en-us/community/posts/207086955-How-to-get-all-variables-within-a-PsiClass-global-and-local-variables-
 * @Reference https://intellij-support.jetbrains.com/hc/en-us/community/posts/206771545-How-do-I-get-method-list-from-PsiFile-or-VirtualFile-
 */
public class ParseAllFile {

    /**
     * 获取指定文件的成员变量。
     *
     * @param psiFile
     * @return
     */
    public static List<PsiField> gerVariablesList(PsiFile psiFile) {
        LogUtil.log("gerVariablesList->" + psiFile.getName());
        MyPsiRecursiveElementVisitor visitor = new MyPsiRecursiveElementVisitor();
        psiFile.accept(visitor);
        return visitor.getPsiFields();
    }

    /**
     * 获取引用
     *
     * @param element
     * @param ctx
     * @return
     */
    public static List<UsageInfo> findUsages(PsiElement element, PsiElement ctx) {
        LogUtil.log("findUsages->" + element.getText());
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
            LogUtil.log("find ->" + usageInfo.getReference());
            usages.add(usageInfo);
        }
        return usages;
    }


    /**
     * 获取引用
     *
     * @param element
     * @return
     */
    public static void findUsage(PsiField element, Project project) {
        LogUtil.log("findUsages for: " + element.getText());
//    Module mod = ModuleUtilCore.findModuleForPsiElement( element );
//    if( mod == null )
//    {
//      return Collections.emptyList();
//    }
//    PsiVariable parentOfType = PsiTreeUtil.getParentOfType(element, PsiVariable.class);
//    if (Objects.isNull(parentOfType)){
//        LogUtil.log("parentOfType is null " + element.getText());
//        return;
//    }

        final GlobalSearchScope projectScope = GlobalSearchScope.projectScope(project);
        Query<PsiReference> search = ReferencesSearch.search(element);

        Collection<PsiReference> all = search.findAll();
        LogUtil.log("reference amount: " + all.size());
        for (PsiReference reference : all) {
            LogUtil.log("\t reference as belows: ->"
                            + reference.getElement().getText() // 引用内容
                            + " \n" + reference.getElement().getParent().getText()
                            + " \n" + reference.getElement().getParent().getParent().getText()
                            + " \n" + reference.getElement().getParent().getParent().getParent().getText()
//                    + " " + reference.resolve().getParent().getText()
//                    + " " + reference.resolve().getText() // 定义的内容
//                    + " " + reference.getCanonicalText() // 引用的内容
//                    + " " + reference.getRangeInElement().toString()
//                    + " " + reference.toString()
//                    + " " + reference.isSoft()
//                    + " " + reference.getVariants().length
            );
        }
//
//        PsiVariable parentOfType = PsiTreeUtil.getParentOfType(element, PsiVariable.class);
//        Query<PsiReference> searchAll = ReferencesSearch.search(parentOfType);
//        for (PsiReference psiReference : searchAll){
//            LogUtil.log("reference->" + psiReference.resolve().toString());
//        }
//        System.out.println("all.size() = " + searchAll.findAll().size());

    }
}
