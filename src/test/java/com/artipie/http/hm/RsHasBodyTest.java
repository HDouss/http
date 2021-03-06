/*
 * MIT License
 *
 * Copyright (c) 2020 Artipie
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.artipie.http.hm;

import com.artipie.http.Response;
import com.artipie.http.rs.RsStatus;
import io.reactivex.Flowable;
import java.nio.ByteBuffer;
import java.util.Collections;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link RsHasBody}.
 *
 * @since 0.4
 */
class RsHasBodyTest {

    @Test
    void shouldMatchEqualBody() {
        final Response response = connection -> connection.accept(
            RsStatus.OK,
            Collections.emptyList(),
            Flowable.fromArray(
                ByteBuffer.wrap("he".getBytes()),
                ByteBuffer.wrap("ll".getBytes()),
                ByteBuffer.wrap("o".getBytes())
            )
        );
        MatcherAssert.assertThat(
            "Matcher is expected to match response with equal body",
            new RsHasBody("hello".getBytes()).matches(response),
            new IsEqual<>(true)
        );
    }

    @Test
    void shouldNotMatchNotEqualBody() {
        final Response response = connection -> connection.accept(
            RsStatus.OK,
            Collections.emptyList(),
            Flowable.fromArray(ByteBuffer.wrap("1".getBytes()))
        );
        MatcherAssert.assertThat(
            "Matcher is expected not to match response with not equal body",
            new RsHasBody("2".getBytes()).matches(response),
            new IsEqual<>(false)
        );
    }

}
