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

package com.perl5.lang.perl.cpan.adapter;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.util.NlsSafe;
import com.intellij.util.containers.ContainerUtil;
import com.perl5.lang.perl.adapters.PackageManagerAdapter;
import com.perl5.lang.perl.adapters.PackageManagerAdapterFactory;
import com.perl5.lang.perl.idea.project.PerlProjectManager;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.VisibleForTesting;

import java.util.Collection;
import java.util.List;

public class CpanAdapter extends PackageManagerAdapter {
  private static final @NlsSafe String PACKAGE_NAME = "CPAN";
  public static final @NlsSafe String SCRIPT_NAME = "cpan";
  private static final @NlsSafe String NO_TEST_ARGUMENT = "-T";

  public CpanAdapter(@NotNull Sdk sdk, @Nullable Project project) {
    super(sdk, project);
  }

  @Override
  public @NotNull String getPresentableName() {
    return "cpan";
  }

  @Override
  protected @NotNull String getManagerScriptName() {
    return SCRIPT_NAME;
  }

  @Override
  protected @NotNull String getManagerPackageName() {
    return PACKAGE_NAME;
  }

  @Override
  protected @NotNull List<String> getInstallParameters(@NotNull Collection<String> packageNames, boolean suppressTests) {
    var result = super.getInstallParameters(packageNames, suppressTests);
    return suppressTests ? ContainerUtil.prepend(result, NO_TEST_ARGUMENT) : result;
  }

  @Contract("null->null")
  public static @Nullable CpanAdapter create(@Nullable Project project) {
    Sdk sdk = PerlProjectManager.getSdk(project);
    return sdk == null ? null : new CpanAdapter(sdk, project);
  }

  public static final class Factory implements PackageManagerAdapterFactory<CpanAdapter> {
    @Override
    public @NotNull CpanAdapter createAdapter(@NotNull Sdk sdk, @Nullable Project project) {
      return new CpanAdapter(sdk, project);
    }

    @VisibleForTesting
    public static @NotNull Factory getInstance() {
      return PackageManagerAdapterFactory.findInstance(Factory.class);
    }
  }
}
