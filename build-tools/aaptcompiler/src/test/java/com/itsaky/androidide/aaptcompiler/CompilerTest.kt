/*
 *  This file is part of AndroidIDE.
 *
 *  AndroidIDE is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  AndroidIDE is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *   along with AndroidIDE.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.itsaky.androidide.aaptcompiler

import com.android.aaptcompiler.BlameLogger
import com.android.aaptcompiler.ResourceCompilerOptions
import com.android.aaptcompiler.compileResource
import com.android.utils.StdLogger
import com.android.utils.StdLogger.Level.VERBOSE
import java.nio.file.Paths
import kotlin.io.path.absolute
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/** @author Akash Yadav */
@RunWith(JUnit4::class)
class CompilerTest {

  @Test
  fun `test simple compilatation`() {
    val input =
      Paths.get("../../app/src/main/res/layout/activity_editor.xml").absolute().normalize().toFile()
    val output = Paths.get("./build").absolute().normalize().toFile()
    println(input)
    println(output)

    val start = System.nanoTime()
    compileResource(input, output, ResourceCompilerOptions(), BlameLogger(StdLogger(VERBOSE)))
    println("Compiled in ${System.nanoTime() - start} nano seconds")
  }
}
