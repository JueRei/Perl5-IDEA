/*
 * Copyright 2015-2023 Alexandr Evstigneev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.perl5.lang.perl.cpanminus.cpanfile;

import com.intellij.psi.PsiFile;
import com.perl5.lang.perl.extensions.imports.PerlImportsProvider;
import com.perl5.lang.perl.extensions.packageprocessor.PerlExportDescriptor;
import com.perl5.lang.perl.psi.PerlNamespaceDefinitionElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PerlCpanfileImportProvider implements PerlImportsProvider {
  private static final String NAMESPACE = "Module::CPANfile::Environment";
  private static final List<String> BINDINGS = Arrays.asList(
    "on",
    "requires",
    "recommends",
    "suggests",
    "conflicts",
    "feature",
    "osname",
    "mirror",
    "configure_requires",
    "build_requires",
    "test_requires",
    "author_requires"
  );

  private static final List<PerlExportDescriptor> DESCRIPTORS = new ArrayList<>();

  static {
    BINDINGS.forEach(name -> DESCRIPTORS.add(PerlExportDescriptor.create(NAMESPACE, name)));
  }

  @Override
  public @NotNull List<PerlExportDescriptor> getExportDescriptors(PerlNamespaceDefinitionElement namespaceElement) {
    return DESCRIPTORS;
  }

  @Override
  public boolean isApplicable(@Nullable PerlNamespaceDefinitionElement namespaceDefinitionElement) {
    if (!(namespaceDefinitionElement instanceof PsiFile)) {
      return false;
    }
    return ((PsiFile)namespaceDefinitionElement).getOriginalFile().getFileType() == PerlFileTypeCpanfile.INSTANCE;
  }
}
