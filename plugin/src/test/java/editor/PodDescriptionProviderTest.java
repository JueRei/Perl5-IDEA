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

package editor;


import base.PodLightTestCase;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
public class PodDescriptionProviderTest extends PodLightTestCase {
  @Override
  protected String getBaseDataPath() {
    return "descriptionProvider/pod";
  }

  @Test
  public void testHeader1() {doTest("=head1 Some <caret>C<header>");}

  @Test
  public void testHeaderWithIndex() {
    doTest("=head1 Header <caret>C<with index>\n" +
           "X<someindex>");
  }

  @Test
  public void testHeader2() {doTest("=head2 Some <caret>C<header>");}

  @Test
  public void testHeader3() {doTest("=head3 Some <caret>C<header>");}

  @Test
  public void testHeader4() {doTest("=head4 Some <caret>C<header>");}

  @Test
  public void testItem() {
    doTest("""
             =over

             =item Some <caret>C<item>

             =back""");
  }

  @Test
  public void testHeaderIndex() {
    doTest("=head1 Some C<header>\n" +
           "X<in<caret>dex>");
  }

  @Test
  public void testItemIndex() {
    doTest("""
             =over

             =item Some <caret>C<item>
             X<in<caret>dex>

             =back""");
  }

  private void doTest(@NotNull String content) {
    doElementDescriptionTest(content);
  }
}