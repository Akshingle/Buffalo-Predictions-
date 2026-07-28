# Buffalo Milk Predictor - Testing Guide

## Test Coverage Summary

This document provides comprehensive testing procedures for all features of the Buffalo Milk Predictor application.

## 1. Environment Setup Tests

### Test 1.1: Project Builds Successfully
**Steps:**
1. Open terminal in project root
2. Run: `./gradlew clean build`

**Expected Result:**
- Build completes without errors
- Build completes without critical warnings
- APK is generated in `app/build/outputs/apk/`

### Test 1.2: Dependencies Resolve Correctly
**Steps:**
1. Run: `./gradlew dependencies`
2. Check for dependency conflicts

**Expected Result:**
- All dependencies resolve successfully
- No version conflicts reported

## 2. Installation Tests

### Test 2.1: Install on Physical Device
**Steps:**
1. Connect Android device (API 24+)
2. Enable Developer Mode and USB Debugging
3. Run: `./gradlew installDebug`
4. Open app from launcher

**Expected Result:**
- APK installs successfully
- App launches without crashes
- Welcome screen appears

### Test 2.2: Install on Emulator
**Steps:**
1. Create AVD with API 28 or higher
2. Start emulator
3. Run: `./gradlew installDebug`
4. Tap app icon

**Expected Result:**
- App installs and launches
- No crashes on startup
- All UI elements render correctly

## 3. Permission Tests

### Test 3.1: Camera Permission
**Steps:**
1. Run app for first time
2. Navigate to Capture screen
3. When prompted, grant camera permission
4. When prompted again, deny camera permission
5. Try to access camera

**Expected Result:**
- Request dialog appears
- Permission granted: Camera works
- Permission denied: Shows error message
- Can still navigate the app

### Test 3.2: Storage Permission (Android 10+)
**Steps:**
1. First run of app
2. Navigate to analyze buffalo
3. Take photo
4. Check saved files

**Expected Result:**
- App has proper storage access
- Photos saved to app's external files directory
- No crashes due to permission issues

## 4. Database Tests

### Test 4.1: Database Creation
**Steps:**
1. Install and run app
2. Connect device via adb: `adb shell`
3. Check database: `sqlite3 /data/data/com.buffalomilkpredictor/databases/buffalo_database`

**Expected Result:**
- Database file created successfully
- buffalo_analysis table exists with correct schema
- All columns present

### Test 4.2: Data Persistence
**Steps:**
1. Complete an analysis
2. Close app completely
3. Open app again
4. Navigate to History

**Expected Result:**
- Previous analysis appears in history
- All data fields preserved correctly
- Date and time accurate

### Test 4.3: Data Operations
**Steps:**
1. Create 5 analyses
2. Delete one analysis
3. Verify history updated
4. Query analyses by breed
5. Query analyses by date range

**Expected Result:**
- CRUD operations work correctly
- Database queries return expected results
- No data corruption

## 5. UI/UX Tests

### Test 5.1: Home Screen Navigation
**Steps:**
1. Launch app
2. Verify home screen displays
3. Click "Analyze Buffalo"
4. Back button returns to home
5. Click "History"
6. Back button returns to home
7. Click "Settings"
8. Back button returns to home

**Expected Result:**
- All navigation works
- Back navigation works correctly
- No UI glitches
- Proper screen transitions

### Test 5.2: Screen Responsiveness
**Steps:**
1. Rotate device between portrait and landscape
2. Verify all screens adapt
3. Check text readability
4. Verify button accessibility

**Expected Result:**
- Layouts adapt to screen orientation
- Text is readable in both orientations
- Buttons remain clickable
- No content cutoff

### Test 5.3: Dark Mode
**Steps:**
1. Enable dark mode in system settings
2. Open app
3. Navigate through screens
4. Verify colors and contrast

**Expected Result:**
- App respects system dark mode
- Colors are appropriate for dark theme
- Text contrast is adequate
- No unreadable elements

## 6. Camera Tests

### Test 6.1: Camera Preview
**Steps:**
1. Navigate to Capture screen
2. Grant camera permission
3. Observe camera preview
4. Rotate device

**Expected Result:**
- Camera preview appears immediately
- Preview updates smoothly
- Preview rotates with device
- No lag or stuttering

### Test 6.2: Photo Capture
**Steps:**
1. On Capture screen
2. Frame a test object (photo of buffalo/animal)
3. Tap capture button
4. Review captured photo
5. Retake photo
6. Save photo

**Expected Result:**
- Photo captures successfully
- Preview shows captured image
- Retake works
- Photo saved to correct location

### Test 6.3: Multiple Photos
**Steps:**
1. Capture 5 photos from different angles
2. Verify all photos saved
3. Check file sizes are reasonable
4. Proceed with analysis

**Expected Result:**
- All 5 photos captured successfully
- File sizes between 500KB-2MB
- Photos are clear and in-focus
- No duplicate files

## 7. ML Analysis Tests

### Test 7.1: Analysis Initiation
**Steps:**
1. Capture at least one photo
2. Enter optional notes
3. Tap "Analyze" button
4. Observe loading screen

**Expected Result:**
- Analysis starts immediately
- Loading indicator appears
- No crashes during processing
- Reasonable processing time (< 30 seconds)

### Test 7.2: Analysis Results Display
**Steps:**
1. Wait for analysis to complete
2. Review results screen
3. Check all fields populated:
   - Breed
   - Scores (0-1)
   - Milk prediction
   - Recommendation

**Expected Result:**
- All fields display values
- Scores between 0-1
- Milk range is valid (8-22+ L)
- Recommendation is one of 5 options

### Test 7.3: Scoring Logic
**Steps:**
1. Perform 3 analyses
2. Document scores for each
3. Verify weighted calculation

**Expected Result:**
- Scores consistently calculated
- High-quality images get higher scores
- Weighted formula applied correctly

## 8. PDF Report Tests

### Test 8.1: PDF Generation
**Steps:**
1. Complete an analysis
2. Tap "Generate Report" button
3. Wait for generation
4. Verify file created

**Expected Result:**
- Report generates successfully
- File saved to Documents folder
- File size > 1MB (includes images)
- File opens in PDF viewer

### Test 8.2: PDF Content
**Steps:**
1. Open generated PDF
2. Verify pages contain:
   - Title and date
   - Buffalo information
   - Analysis scores
   - Milk prediction
   - Recommendation
   - Images
   - Notes

**Expected Result:**
- All sections present
- Formatting is professional
- Images are clear
- Text is readable
- No corrupted content

### Test 8.3: PDF Sharing
**Steps:**
1. Open PDF
2. Tap share button
3. Select email/messaging app
4. Send PDF

**Expected Result:**
- Share dialog appears
- PDF sends successfully
- Recipient can open PDF
- No attachment issues

## 9. Multi-Language Tests

### Test 9.1: English Language
**Steps:**
1. Set system language to English
2. Launch app
3. Verify all text in English
4. Check string accuracy

**Expected Result:**
- All UI text in English
- No untranslated strings
- Text formatting correct

### Test 9.2: Hindi Language
**Steps:**
1. Set system language to Hindi
2. Launch app
3. Verify all text in Hindi
4. Check Devanagari script

**Expected Result:**
- All UI text in Hindi
- Proper Devanagari rendering
- No character corruption
- Text flows correctly

### Test 9.3: Marathi Language
**Steps:**
1. Set system language to Marathi
2. Launch app
3. Verify all text in Marathi
4. Check Devanagari script

**Expected Result:**
- All UI text in Marathi
- Proper Devanagari rendering
- Special characters display correctly

### Test 9.4: Language Switching
**Steps:**
1. Start in English
2. Change to Hindi in settings
3. Verify UI updates immediately
4. Switch to Marathi
5. Verify update

**Expected Result:**
- Language changes instantly
- All screens update
- No need to restart app
- Preference persists

## 10. History Management Tests

### Test 10.1: History Display
**Steps:**
1. Create 5 analyses
2. Navigate to History
3. Verify all appear in reverse chronological order
4. Tap on one analysis

**Expected Result:**
- All 5 analyses listed
- Newest first
- Tapping opens detailed view
- Date and breed displayed

### Test 10.2: History Search
**Steps:**
1. Create analyses of different breeds
2. Use search/filter by breed
3. Clear search

**Expected Result:**
- Search filters results correctly
- Only matching breeds shown
- Clear search restores full list

### Test 10.3: History Deletion
**Steps:**
1. From history, select analysis
2. Swipe to delete / tap delete button
3. Confirm deletion
4. Verify removed from list

**Expected Result:**
- Delete confirmation appears
- Analysis removed from history
- Database updated
- Refresh shows it's gone

## 11. Settings Tests

### Test 11.1: Dark Mode Toggle
**Steps:**
1. Navigate to Settings
2. Enable dark mode
3. Verify theme changes
4. Disable dark mode
5. Verify theme changes back

**Expected Result:**
- Theme changes immediately
- All screens update
- Colors are appropriate
- Setting persists

### Test 11.2: Language Selection
**Steps:**
1. Navigate to Settings
2. Tap Language dropdown
3. Select Hindi
4. Verify UI updates
5. Select Marathi
6. Verify updates
7. Select English

**Expected Result:**
- Dropdown shows all 3 languages
- Selection changes language immediately
- All screens update
- Selection persists across restarts

## 12. Edge Case & Error Handling Tests

### Test 12.1: No Images Selected
**Steps:**
1. Navigate to Capture
2. Tap Analyze without selecting images

**Expected Result:**
- Error message appears
- App doesn't crash
- Can select images and retry

### Test 12.2: Low Storage Space
**Steps:**
1. Fill device storage to ~100MB remaining
2. Try to save analysis
3. Try to generate PDF

**Expected Result:**
- App handles gracefully
- Error message shown
- App remains stable
- Can proceed after freeing space

### Test 12.3: Camera Disconnection
**Steps:**
1. Start using camera
2. Close camera app (if multi-app)
3. Return to app

**Expected Result:**
- App detects camera unavailable
- Shows appropriate error
- Can retry
- No crashes

### Test 12.4: Corrupted Images
**Steps:**
1. Load invalid/corrupted image file
2. Try to analyze

**Expected Result:**
- App detects corruption
- Shows error message
- Allows selection of different image

## 13. Performance Tests

### Test 13.1: Startup Time
**Steps:**
1. Cold start app
2. Measure time to home screen

**Expected Result:**
- Startup < 3 seconds
- No frozen UI
- Smooth animations

### Test 13.2: Analysis Processing Time
**Steps:**
1. With 3 images, analyze
2. Measure time to results

**Expected Result:**
- Processing < 30 seconds
- No UI freezing
- Progress indication clear

### Test 13.3: Memory Usage
**Steps:**
1. Complete 10 analyses
2. Monitor RAM in developer settings
3. Check for memory leaks

**Expected Result:**
- RAM usage < 200MB
- No constant increase
- Garbage collection working

### Test 13.4: Battery Consumption
**Steps:**
1. Run app for 10 minutes
2. Monitor battery drain
3. Compare with idle battery drain

**Expected Result:**
- Battery drain < 5% in 10 minutes
- No excessive CPU usage
- Camera optimized

## 14. Compilation & Build Tests

### Test 14.1: No Compilation Errors
**Steps:**
1. Run: `./gradlew build`
2. Check output

**Expected Result:**
- Zero errors
- Warnings acceptable but minimal
- Build completes successfully

### Test 14.2: Lint Checks
**Steps:**
1. Run: `./gradlew lint`
2. Review lint report

**Expected Result:**
- No critical issues
- No security vulnerabilities
- Performance warnings addressed

### Test 14.3: Code Coverage
**Steps:**
1. Run unit tests
2. Check code coverage report

**Expected Result:**
- Core business logic covered
- Critical paths tested
- > 70% coverage target

## 15. Data Validation Tests

### Test 15.1: Score Ranges
**Steps:**
1. Complete 5 analyses
2. Check all scores

**Expected Result:**
- All scores between 0.0 and 1.0
- Weighted total = overall score
- No negative values

### Test 15.2: Milk Prediction Mapping
**Steps:**
1. Verify mapping:
   - 0.85+ → 22+ L (Excellent)
   - 0.70-0.85 → 18-22 L (Good)
   - 0.55-0.70 → 14-18 L (Average)
   - 0.40-0.55 → 10-14 L (Below Average)
   - <0.40 → 8-10 L (Poor)

**Expected Result:**
- Mapping is accurate
- Scores map correctly
- Ranges are logical

### Test 15.3: Date/Time Consistency
**Steps:**
1. Create analysis
2. Check saved date/time
3. Compare with current

**Expected Result:**
- Dates saved correctly
- Times accurate
- Timezone handled correctly
- Format consistent

## Test Report Template

```
Buffalo Milk Predictor - Test Execution Report
Date: [Date]
Build Version: [Version]
Device: [Device/Emulator]
Android Version: [Version]
Tester: [Name]

Total Tests: XX
Passed: XX
Failed: XX
Skipped: XX
Pass Rate: XX%

Critical Issues Found:
- [Issue 1]
- [Issue 2]

Known Limitations:
- [Limitation 1]
- [Limitation 2]

Recommendations:
- [Recommendation 1]
- [Recommendation 2]
```

## Regression Testing Checklist

After any code changes, verify:
- [ ] App builds successfully
- [ ] Camera works
- [ ] Photos save correctly
- [ ] Analysis completes
- [ ] Database stores data
- [ ] History displays correctly
- [ ] PDF generation works
- [ ] Multi-language support intact
- [ ] Settings persist
- [ ] No new crashes

## Continuous Testing Best Practices

1. **Automate Unit Tests**: Run before every commit
2. **Device Testing**: Test on multiple Android versions
3. **Edge Cases**: Always test boundary conditions
4. **Performance Monitoring**: Track metrics over time
5. **User Feedback**: Collect and address user reports

---

**Test Status**: ✅ Ready for Testing
**Last Updated**: [Date]
**Next Review**: [Date]
