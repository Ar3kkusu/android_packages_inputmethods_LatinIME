/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.inputmethod.latin.personalization;

import com.android.inputmethod.latin.utils.CollectionUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.lang.ref.SoftReference;
import java.util.concurrent.ConcurrentHashMap;

public class PersonalizationDictionaryHelper {
    private static final String TAG = PersonalizationDictionaryHelper.class.getSimpleName();
    private static final boolean DEBUG = false;

    private static final ConcurrentHashMap<String, SoftReference<UserHistoryPredictionDictionary>>
            sLangUserHistoryDictCache = CollectionUtils.newConcurrentHashMap();

    private static final ConcurrentHashMap<String,
            SoftReference<PersonalizationPredictionDictionary>>
                    sLangPersonalizationDictCache = CollectionUtils.newConcurrentHashMap();

    public static UserHistoryPredictionDictionary getUserHistoryPredictionDictionary(
            final Context context, final String locale, final SharedPreferences sp) {
        synchronized (sLangUserHistoryDictCache) {
            if (sLangUserHistoryDictCache.containsKey(locale)) {
                final SoftReference<UserHistoryPredictionDictionary> ref =
                        sLangUserHistoryDictCache.get(locale);
                final UserHistoryPredictionDictionary dict = ref == null ? null : ref.get();
                if (dict != null) {
                    if (DEBUG) {
                        Log.w(TAG, "Use cached UserHistoryPredictionDictionary for " + locale);
                    }
                    return dict;
                }
            }
            final UserHistoryPredictionDictionary dict =
                    new UserHistoryPredictionDictionary(context, locale, sp);
            sLangUserHistoryDictCache.put(
                    locale, new SoftReference<UserHistoryPredictionDictionary>(dict));
            return dict;
        }
    }

    public static PersonalizationPredictionDictionary getPersonalizationPredictionDictionary(
            final Context context, final String locale, final SharedPreferences sp) {
        synchronized (sLangPersonalizationDictCache) {
            if (sLangPersonalizationDictCache.containsKey(locale)) {
                final SoftReference<PersonalizationPredictionDictionary> ref =
                        sLangPersonalizationDictCache.get(locale);
                final PersonalizationPredictionDictionary dict = ref == null ? null : ref.get();
                if (dict != null) {
                    if (DEBUG) {
                        Log.w(TAG, "Use cached PersonalizationPredictionDictionary for " + locale);
                    }
                    return dict;
                }
            }
            final PersonalizationPredictionDictionary dict =
                    new PersonalizationPredictionDictionary(context, locale, sp);
            sLangPersonalizationDictCache.put(
                    locale, new SoftReference<PersonalizationPredictionDictionary>(dict));
            return dict;
        }
    }
}