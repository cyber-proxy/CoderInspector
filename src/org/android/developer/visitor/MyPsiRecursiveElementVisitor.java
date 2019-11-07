package org.android.developer.visitor;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiRecursiveElementVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LC
 * @des
 */
public class MyPsiRecursiveElementVisitor extends PsiRecursiveElementVisitor {
    private List<PsiField> psiFields = new ArrayList<PsiField>();

    public List<PsiField> getPsiFields() {
        return psiFields;
    }

    public MyPsiRecursiveElementVisitor() {
        super();
    }

    @Override
    public void visitElement(PsiElement element) {
        if (element instanceof PsiField) {
            PsiField psiField = (PsiField) element;
            psiFields.add(psiField);
        }
        super.visitElement(element);
    }

    @Override
    public void visitFile(PsiFile file) {
        super.visitFile(file);
    }
}
