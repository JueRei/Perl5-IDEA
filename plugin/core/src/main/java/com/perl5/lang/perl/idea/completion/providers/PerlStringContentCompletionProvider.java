/*
 * Copyright 2015-2020 Alexandr Evstigneev
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

package com.perl5.lang.perl.idea.completion.providers;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLanguageInjectionHost;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import com.perl5.lang.perl.idea.completion.providers.processors.PerlSimpleCompletionProcessor;
import com.perl5.lang.perl.idea.completion.providers.processors.PerlVariableCompletionProcessorImpl;
import com.perl5.lang.perl.idea.completion.util.PerlPackageCompletionUtil;
import com.perl5.lang.perl.idea.completion.util.PerlStringCompletionUtil;
import com.perl5.lang.perl.idea.completion.util.PerlSubCompletionUtil;
import com.perl5.lang.perl.idea.completion.util.PerlVariableCompletionUtil;
import com.perl5.lang.perl.psi.PsiPerlAnnotationInject;
import com.perl5.lang.perl.psi.PsiPerlGlobSlot;
import com.perl5.lang.perl.psi.PsiPerlHashIndex;
import com.perl5.lang.perl.util.PerlInjectionUtil;
import com.perl5.lang.perl.util.PerlTimeLogger;
import org.jetbrains.annotations.NotNull;

import static com.perl5.lang.perl.idea.PerlElementPatterns.*;


public class PerlStringContentCompletionProvider extends PerlCompletionProvider {
  @Override
  protected void addCompletions(@NotNull CompletionParameters parameters,
                                @NotNull ProcessingContext context,
                                final @NotNull CompletionResultSet result) {
    PsiElement element = parameters.getPosition();
    PsiElement parent = element.getParent();

    if (parent instanceof PsiLanguageInjectionHost && PerlInjectionUtil.hasInjections(parent)) {
      return;
    }

    PerlTimeLogger logger = PerlTimeLogger.create(LOG);
    PerlSimpleCompletionProcessor completionProcessor = new PerlSimpleCompletionProcessor(parameters, result, element);

    if (EXPORT_ASSIGNED_STRING_CONTENT.accepts(element)) { // exporter assignments
      PerlStringCompletionUtil.fillWithExportableEntities(completionProcessor);
      logger.debug("Filled with exportable entities");
    }
    else if (SIMPLE_HASH_INDEX.accepts(element)) {    // hash indexes
      PsiPerlHashIndex indexElement = PsiTreeUtil.getParentOfType(element, PsiPerlHashIndex.class);
      if (indexElement != null && indexElement.getParent() instanceof PsiPerlGlobSlot) {
        PerlStringCompletionUtil.fillWithRefTypes(completionProcessor);
        logger.debug("Filled with ref types");
      }
      else {
        PerlStringCompletionUtil.fillWithHashIndexes(completionProcessor);
        logger.debug("Filled with hash indexes");
        PerlVariableCompletionUtil.processVariables(
          new PerlVariableCompletionProcessorImpl(completionProcessor, null, false, false, false), logger);
        PerlSubCompletionUtil.processContextSubsLookupElements(completionProcessor);
        logger.debug("Filled with context subs lookup elements");
        PerlPackageCompletionUtil.processAllNamespacesNames(completionProcessor, false, false);
        logger.debug("Filled with namespace names");
      }
    }
    else if (USE_PARAMETERS_PATTERN.accepts(element)) {    // use or no parameters
      PerlStringCompletionUtil.fillWithUseParameters(completionProcessor);
      logger.debug("Filled with use parameters");
    }
    else if (parent != null && parent.getParent() instanceof PsiPerlAnnotationInject) { // #@Inject some
      PerlStringCompletionUtil.fillWithInjectableMarkers(completionProcessor);
      result.stopHere();
      logger.debug("Filled with injectable markers");
    }
    else if (STRING_CONTENT_IN_HEREDOC_OPENER_PATTERN.accepts(element)) { // HERE-DOC openers
      PerlStringCompletionUtil.fillWithInjectableMarkers(completionProcessor);
      logger.debug("Filled with injectable markers in opener");
      PerlStringCompletionUtil.fillWithHeredocOpeners(completionProcessor);
      logger.debug("Filled with heredoc openers");
    }
    else if (STRING_CONTENT_IN_LIST_OR_STRING_START.accepts(element)) {    // begin of string or qw element
      PerlStringCompletionUtil.fillWithRefTypes(completionProcessor);
      logger.debug("Filled with ref types");
      PerlPackageCompletionUtil.processAllNamespacesNames(completionProcessor);
      logger.debug("Processed namespace names");
    }
    completionProcessor.logStatus(getClass());
  }
}
