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

package com.perl5.lang.pod.elementTypes;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.PsiElement;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.tree.IElementType;
import com.perl5.lang.pod.parser.psi.impl.PodIdentifierImpl;
import com.perl5.lang.pod.parser.psi.mixin.*;
import com.perl5.lang.pod.parser.psi.stubs.PodSectionStub;
import com.perl5.lang.pod.psi.impl.*;
import org.jetbrains.annotations.NotNull;


public class PodElementTypeFactory {
  private static final Logger LOG = Logger.getInstance(PodElementTypeFactory.class);

  public static IElementType getTokenType(String debugName) {
    if (debugName.equals("identifier")) {
      return new PodTokenTypeEx(debugName) {
        @Override
        public @NotNull ASTNode createLeafNode(@NotNull CharSequence leafText) {
          return new PodIdentifierImpl(this, leafText);
        }
      };
    }
    return new PodTokenType(debugName);
  }

  public static IElementType getElementType(String name) {
    if (name.equals("POD_FORMAT_INDEX")) {
      return new PodFormatterXElementType(name);
    }

    if (name.equals("CUT_SECTION")) {
      return new PodElementType(name) {
        @Override
        public @NotNull PsiElement getPsiElement(@NotNull ASTNode node) {
          return new PsiCutSectionImpl(node);
        }
      };
    }

    if (name.equals("ENCODING_SECTION")) {
      return new PodElementType(name) {
        @Override
        public @NotNull PsiElement getPsiElement(@NotNull ASTNode node) {
          return new PsiEncodingSectionImpl(node);
        }
      };
    }

    if (name.equals("FORMATTING_SECTION_CONTENT")) {
      return new PodElementType(name) {
        @Override
        public @NotNull PsiElement getPsiElement(@NotNull ASTNode node) {
          return new PsiFormattingSectionContentImpl(node);
        }
      };
    }

    if (name.equals("FOR_SECTION")) {
      return new PodElementType(name) {
        @Override
        public @NotNull PsiElement getPsiElement(@NotNull ASTNode node) {
          return new PsiForSectionImpl(node);
        }
      };
    }

    if (name.equals("FOR_SECTION_CONTENT")) {
      return new PodElementType(name) {
        @Override
        public @NotNull PsiElement getPsiElement(@NotNull ASTNode node) {
          return new PsiForSectionContentImpl(node);
        }
      };
    }

    if (name.equals("HEAD_1_SECTION")) {
      return new PodStubBasedTitledSectionElementType<PodSectionH1>(name) {
        @Override
        public PodSectionH1 createPsi(@NotNull PodSectionStub stub) {
          return new PsiHead1SectionImpl(stub, this);
        }

        @Override
        public @NotNull PsiElement getPsiElement(@NotNull ASTNode node) {
          return new PsiHead1SectionImpl(node);
        }
      };
    }

    if (name.equals("HEAD_1_SECTION_CONTENT")) {
      return new PodElementType(name) {
        @Override
        public @NotNull PsiElement getPsiElement(@NotNull ASTNode node) {
          return new PsiHead1SectionContentImpl(node);
        }
      };
    }

    if (name.equals("HEAD_2_SECTION")) {
      return new PodStubBasedTitledSectionElementType<PodSectionH2>(name) {
        @Override
        public PodSectionH2 createPsi(@NotNull PodSectionStub stub) {
          return new PsiHead2SectionImpl(stub, this);
        }

        @Override
        public @NotNull PsiElement getPsiElement(@NotNull ASTNode node) {
          return new PsiHead2SectionImpl(node);
        }
      };
    }

    if (name.equals("HEAD_2_SECTION_CONTENT")) {
      return new PodElementType(name) {
        @Override
        public @NotNull PsiElement getPsiElement(@NotNull ASTNode node) {
          return new PsiHead2SectionContentImpl(node);
        }
      };
    }

    if (name.equals("HEAD_3_SECTION")) {
      return new PodStubBasedTitledSectionElementType<PodSectionH3>(name) {
        @Override
        public PodSectionH3 createPsi(@NotNull PodSectionStub stub) {
          return new PsiHead3SectionImpl(stub, this);
        }

        @Override
        public @NotNull PsiElement getPsiElement(@NotNull ASTNode node) {
          return new PsiHead3SectionImpl(node);
        }
      };
    }

    if (name.equals("HEAD_3_SECTION_CONTENT")) {
      return new PodElementType(name) {
        @Override
        public @NotNull PsiElement getPsiElement(@NotNull ASTNode node) {
          return new PsiHead3SectionContentImpl(node);
        }
      };
    }

    if (name.equals("HEAD_4_SECTION")) {
      return new PodStubBasedTitledSectionElementType<PodSectionH4>(name) {
        @Override
        public PodSectionH4 createPsi(@NotNull PodSectionStub stub) {
          return new PsiHead4SectionImpl(stub, this);
        }

        @Override
        public @NotNull PsiElement getPsiElement(@NotNull ASTNode node) {
          return new PsiHead4SectionImpl(node);
        }
      };
    }

    if (name.equals("HEAD_4_SECTION_CONTENT")) {
      return new PodElementType(name) {
        @Override
        public @NotNull PsiElement getPsiElement(@NotNull ASTNode node) {
          return new PsiHead4SectionContentImpl(node);
        }
      };
    }

    if (name.equals("ITEM_SECTION")) {
      return new PodSectionItemElementType(name);
    }

    if (name.equals("ITEM_SECTION_CONTENT")) {
      return new PodElementType(name) {
        @Override
        public @NotNull PsiElement getPsiElement(@NotNull ASTNode node) {
          return new PsiItemSectionContentImpl(node);
        }
      };
    }

    if (name.equals("ITEM_SECTION_TITLE")) {
      return new PodElementType(name) {
        @Override
        public @NotNull PsiElement getPsiElement(@NotNull ASTNode node) {
          return new PsiItemSectionTitleImpl(node);
        }
      };
    }

    if (name.equals("OVER_SECTION")) {
      return new PodElementType(name) {
        @Override
        public @NotNull PsiElement getPsiElement(@NotNull ASTNode node) {
          return new PsiOverSectionImpl(node);
        }
      };
    }

    if (name.equals("OVER_SECTION_CONTENT")) {
      return new PodElementType(name) {
        @Override
        public @NotNull PsiElement getPsiElement(@NotNull ASTNode node) {
          return new PsiOverSectionContentImpl(node);
        }
      };
    }

    if (name.equals("POD_VERBATIM_PARAGRAPH")) {
      return new PodElementType(name) {
        @Override
        public @NotNull PsiElement getPsiElement(@NotNull ASTNode node) {
          return new PsiPodVerbatimParagraphImpl(node);
        }
      };
    }

    if (name.equals("POD_FORMAT_BOLD")) {
      return new PodElementType(name) {
        @Override
        public @NotNull PsiElement getPsiElement(@NotNull ASTNode node) {
          return new PsiPodFormatBoldImpl(node);
        }
      };
    }

    if (name.equals("POD_FORMAT_CODE")) {
      return new PodElementType(name) {
        @Override
        public @NotNull PsiElement getPsiElement(@NotNull ASTNode node) {
          return new PsiPodFormatCodeImpl(node);
        }
      };
    }

    if (name.equals("POD_FORMAT_ESCAPE")) {
      return new PodElementType(name) {
        @Override
        public @NotNull PsiElement getPsiElement(@NotNull ASTNode node) {
          return new PsiPodFormatEscapeImpl(node);
        }
      };
    }

    if (name.equals("POD_FORMAT_FILE")) {
      return new PodElementType(name) {
        @Override
        public @NotNull PsiElement getPsiElement(@NotNull ASTNode node) {
          return new PsiPodFormatFileImpl(node);
        }
      };
    }

    if (name.equals("POD_FORMAT_ITALIC")) {
      return new PodElementType(name) {
        @Override
        public @NotNull PsiElement getPsiElement(@NotNull ASTNode node) {
          return new PsiPodFormatItalicImpl(node);
        }
      };
    }

    if (name.equals("POD_FORMAT_LINK")) {
      return new PodElementType(name) {
        @Override
        public @NotNull PsiElement getPsiElement(@NotNull ASTNode node) {
          return new PsiPodFormatLinkImpl(node);
        }
      };
    }

    if (name.equals("POD_FORMAT_NBSP")) {
      return new PodElementType(name) {
        @Override
        public @NotNull PsiElement getPsiElement(@NotNull ASTNode node) {
          return new PsiPodFormatNbspImpl(node);
        }
      };
    }

    if (name.equals("POD_FORMAT_NULL")) {
      return new PodElementType(name) {
        @Override
        public @NotNull PsiElement getPsiElement(@NotNull ASTNode node) {
          return new PsiPodFormatNullImpl(node);
        }
      };
    }

    if (name.equals("POD_PARAGRAPH")) {
      return new PodStubBasedSectionElementType<PodSectionParagraph>(name) {
        @Override
        public PodSectionParagraph createPsi(@NotNull PodSectionStub stub) {
          return new PsiPodParagraphImpl(stub, this);
        }

        @Override
        public @NotNull PodSectionStub createStub(@NotNull PodSectionParagraph psi, StubElement parentStub) {
          return new PodSectionStub(parentStub, this, psi.getPresentableText());
        }

        @Override
        public @NotNull PsiElement getPsiElement(@NotNull ASTNode node) {
          return new PsiPodParagraphImpl(node);
        }
      };
    }

    if (name.equals("POD_SECTION")) {
      return new PodElementType(name) {
        @Override
        public @NotNull PsiElement getPsiElement(@NotNull ASTNode node) {
          return new PsiPodSectionImpl(node);
        }
      };
    }

    if (name.equals("POD_SECTION_FORMAT")) {
      return new PodElementType(name) {
        @Override
        public @NotNull PsiElement getPsiElement(@NotNull ASTNode node) {
          return new PsiPodSectionFormatImpl(node);
        }
      };
    }

    if (name.equals("SECTION_TITLE")) {
      return new PodElementType(name) {
        @Override
        public @NotNull PsiElement getPsiElement(@NotNull ASTNode node) {
          return new PsiSectionTitleImpl(node);
        }
      };
    }

    if (name.equals("UNKNOWN_SECTION")) {
      return new PodStubBasedTitledSectionElementType<PsiUnknownSectionImpl>(name) {
        @Override
        public PsiUnknownSectionImpl createPsi(@NotNull PodSectionStub stub) {
          return new PsiUnknownSectionImpl(stub, this);
        }

        @Override
        public @NotNull PsiElement getPsiElement(@NotNull ASTNode node) {
          return new PsiUnknownSectionImpl(node);
        }
      };
    }
    if (name.equals("LINK_TEXT")) {
      return new PodElementType(name) {
        @Override
        public @NotNull PsiElement getPsiElement(@NotNull ASTNode node) {
          return new PsiLinkTextImpl(node);
        }
      };
    }
    if (name.equals("LINK_NAME")) {
      return new PodElementType(name) {
        @Override
        public @NotNull PsiElement getPsiElement(@NotNull ASTNode node) {
          return new PsiLinkNameImpl(node);
        }
      };
    }
    if (name.equals("LINK_SECTION")) {
      return new PodElementType(name) {
        @Override
        public @NotNull PsiElement getPsiElement(@NotNull ASTNode node) {
          return new PsiLinkSectionImpl(node);
        }
      };
    }
    if (name.equals("LINK_URL")) {
      return new PodElementType(name) {
        @Override
        public @NotNull PsiElement getPsiElement(@NotNull ASTNode node) {
          return new PsiLinkUrlImpl(node);
        }
      };
    }
    LOG.error("Missing element: " + name);
    throw new RuntimeException();
  }
}
