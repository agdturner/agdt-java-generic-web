/**
 * This is free and unencumbered software released into the public domain.
 *
 * Anyone is free to copy, modify, publish, use, compile, sell, or distribute
 * this software, either in source code form or as a compiled binary, for any
 * purpose, commercial or non-commercial, and by any means.
 *
 * In jurisdictions that recognise copyright laws, the author or authors of this
 * software dedicate any and all copyright interest in the software to the
 * public domain. We make this dedication for the benefit of the public at large
 * and to the detriment of our heirs and successors. We intend this dedication
 * to be an overt act of relinquishment in perpetuity of all present and future
 * rights to this software under copyright law.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * For more information, please refer to <http://unlicense.org/>
 */
package uk.ac.leeds.ccg.andyt.web.houseprices;

import java.util.Iterator;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Class for formatting postcodes of the an_naa format.
 */
public class Web_Run_ann_naa extends Web_AbstractRun {

    /**
     * @param tZooplaHousepriceScraper The Web_ZooplaHousepriceScraper
     * @param restart This is expected to be true if restarting a partially
     * completed run and false otherwise.
     */
    public Web_Run_ann_naa(
            Web_ZooplaHousepriceScraper tZooplaHousepriceScraper,
            boolean restart) {
        init(tZooplaHousepriceScraper, restart);
    }

    @Override
    public void run() {
        if (restart == false) {
            formatNew();
        } else {
            if (firstpartPostcode.length() > 1) {
                throw new NotImplementedException();
            }
            // Initialise output files
            String filenamepart = "_Houseprices_" + firstpartPostcode + "nn";
            String[] postcodeForRestart = getPostcodeForRestart(
                    getType(), filenamepart);
            if (postcodeForRestart == null) {
                formatNew();
            } else {
                int n0Restart = Integer.valueOf(postcodeForRestart[0].substring(1, 2));
                int n1Restart = Integer.valueOf(postcodeForRestart[0].substring(2, 3));
                // Initialise output files
                initialiseOutputs(getType(), filenamepart);
                // Process
                Iterator<String> _NAA_Iterator;
                int n0;
                int n1;
                int _int0;
                String _NAAString;
                int counter = 0;
                int numberOfHousepriceRecords = 0;
                int numberOfPostcodesWithHousepriceRecords = 0;
                boolean n0Restarter = false;
                boolean n1Restarter = false;
                boolean secondPartPostcodeRestarter = false;
                for (n0 = 0; n0 < 10; n0++) {
                    if (!n0Restarter) {
                        if (n0 == n0Restart) {
                            n0Restarter = true;
                        }
                    } else {
                        for (n1 = 0; n1 < 10; n1++) {
                            if (!n1Restarter) {
                                if (n1 == n1Restart) {
                                    n1Restarter = true;
                                }
                            } else {
                                String completeFirstPartPostcode = firstpartPostcode
                                        + Integer.toString(n0) + Integer.toString(n1);
                                String aURLString0 = url + completeFirstPartPostcode;
                                checkRequestRate();
                                if (tZooplaHousepriceScraper.isReturningOutcode(completeFirstPartPostcode, aURLString0)) {
                                    _NAA_Iterator = _NAA.iterator();
                                    while (_NAA_Iterator.hasNext()) {
                                        _NAAString = (String) _NAA_Iterator.next();
                                        if (!secondPartPostcodeRestarter) {
                                            if (_NAAString.equalsIgnoreCase(postcodeForRestart[1])) {
                                                secondPartPostcodeRestarter = true;
                                            }
                                        } else {
                                            _NAAString = (String) _NAA_Iterator.next();
                                            String aURLString = aURLString0 + "-" + _NAAString;
                                            _int0 = tZooplaHousepriceScraper.writeHouseprices(
                                                    outPR,
                                                    logPR,
                                                    sharedLogPR,
                                                    aURLString,
                                                    firstpartPostcode + Integer.toString(n0) + Integer.toString(n1),
                                                    _NAAString,
                                addressAdditionalPropertyDetails);
                                            counter++;
                                            numberOfHousepriceRecords += _int0;
                                            if (_int0 > 0) {
                                                numberOfPostcodesWithHousepriceRecords++;
                                            }
                                        }
                                    }
                                } else {
                                    Web_ZooplaHousepriceScraper.updateLog(
                                            logPR,
                                            sharedLogPR,
                                            completeFirstPartPostcode);
                                }
                            }
                        }
                    }
//            System.out.println(getReportString(
//                    counter,
//                    numberOfHousepriceRecords,
//                    numberOfPostcodesWithHousepriceRecords));
                }
                // Final reporting
                finalise(counter, numberOfHousepriceRecords, numberOfPostcodesWithHousepriceRecords);
            }
        }
    }

    private void formatNew() {
        // Initialise output files
        String filenamepart = "_Houseprices_" + firstpartPostcode + "nn";
        initialiseOutputs("ann", filenamepart);
        if (firstpartPostcode.length() != 1) {
            throw new NotImplementedException();
        } else {
        Iterator<String> _NAA_Iterator;
        int n0;
        int n1;
        int _int0;
        String _NAAString;
        int counter = 0;
        int numberOfHousepriceRecords = 0;
        int numberOfPostcodesWithHousepriceRecords = 0;
        for (n0 = 0; n0 < 10; n0++) {
            for (n1 = 0; n1 < 10; n1++) {
                String completeFirstPartPostcode = firstpartPostcode + Integer.toString(n0)
                        + Integer.toString(n1);
                String aURLString0 = url + completeFirstPartPostcode;
                checkRequestRate();
                if (tZooplaHousepriceScraper.isReturningOutcode(completeFirstPartPostcode, aURLString0)) {
                    _NAA_Iterator = _NAA.iterator();
                    while (_NAA_Iterator.hasNext()) {
                        _NAAString = (String) _NAA_Iterator.next();
                        String aURLString = aURLString0 + "-" + _NAAString;
                        _int0 = tZooplaHousepriceScraper.writeHouseprices(
                                outPR,
                                logPR,
                                sharedLogPR,
                                aURLString,
                                firstpartPostcode + Integer.toString(n0) + Integer.toString(n1),
                                _NAAString,
                                addressAdditionalPropertyDetails);
                        counter++;
                        numberOfHousepriceRecords += _int0;
                        if (_int0 > 0) {
                            numberOfPostcodesWithHousepriceRecords++;
                        }
                    }
                } else {
                    Web_ZooplaHousepriceScraper.updateLog(
                            logPR,
                            sharedLogPR,
                            completeFirstPartPostcode);
                }
            }
//            System.out.println(getReportString(
//                    counter,
//                    numberOfHousepriceRecords,
//                    numberOfPostcodesWithHousepriceRecords));
        }
        // Final reporting
        finalise(counter, numberOfHousepriceRecords, numberOfPostcodesWithHousepriceRecords);
    }
    }
}