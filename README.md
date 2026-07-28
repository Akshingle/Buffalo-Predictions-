# Buffalo Milk Predictor - AI-Powered Dairy Analysis

A comprehensive Android application that uses AI and Computer Vision to predict the milk production potential of buffalo dairy animals based on scientific dairy selection parameters.

## Features

### Core Functionality
- **Multi-angle Image Capture**: Capture photos from front, side, rear, udder, teats, legs, and more
- **AI-Powered Analysis**: Uses machine learning to analyze buffalo characteristics
- **Milk Production Prediction**: Estimates milk production capacity (8-22+ L/day)
- **Breed Identification**: Identifies buffalo breed from images
- **Disease Detection**: Detects visible health issues and defects
- **Buying Recommendation**: Provides expert recommendation (Excellent/Good/Average/Avoid/Reject)

### Analysis Parameters
The app analyzes 17 scientific parameters:
1. Breed Identification
2. Body Frame (length, chest, height, balance)
3. Body Condition Score (BCS 1-5, ideal 2.75-3.5)
4. Dairy Character (angular body, sharp withers, etc.)
5. Udder Analysis (35% weight in scoring)
6. Teat Analysis (15% weight)
7. Milk Vein Prominence
8. Skin Quality
9. Bone Structure
10. Walking Analysis
11. Disease Detection
12. Body Defects
13. Face Analysis
14. Tail Assessment
15. Age Estimation
16. Pregnancy Detection
17. Breed Purity Confidence

### Weighted Scoring Model
- **Udder**: 35%
- **Body Frame**: 15%
- **Teats**: 15%
- **Legs**: 10%
- **Body Condition**: 10%
- **Disease Detection**: 10%
- **Breed**: 5%
- **Walking**: 5%
- **Age**: 5%

### Additional Features
- **PDF Report Generation**: Professional reports with analysis details
- **Analysis History**: Save and track previous analyses
- **Multi-Language Support**: English, Hindi, Marathi
- **Dark Mode**: Supports system dark mode
- **Offline Mode**: Works without internet connection
- **Database Storage**: Room database for persistent storage
- **Image Processing**: Advanced image scaling and enhancement

## Technology Stack

### Architecture
- **MVVM**: Model-View-ViewModel pattern
- **Repository Pattern**: Clean separation of concerns
- **Jetpack Compose**: Modern declarative UI framework
- **Coroutines**: Asynchronous programming

### Libraries
- **CameraX**: Camera functionality
- **Room Database**: Local data persistence
- **TensorFlow Lite**: ML inference
- **MediaPipe**: Computer vision tasks
- **ML Kit**: Google machine learning
- **iTextPDF**: PDF generation
- **DataStore**: Preferences management
- **Accompanist**: Permissions handling
- **Coil**: Image loading

### Languages
- **Kotlin**: Primary programming language
- **XML**: Resource and layout files

## Project Structure

```
app/
├── src/main/
│   ├── kotlin/com/buffalomilkpredictor/
│   │   ├── MainActivity.kt
│   │   ├── data/
│   │   │   ├── database/          # Room database
│   │   │   ├── model/             # Data models
│   │   │   └── repository/        # Data access layer
│   │   ├── ml/
│   │   │   ├── analysis/          # Analysis engine
│   │   │   └── model/             # ML models (breed, disease, body, udder)
│   │   ├── ui/
│   │   │   ├── navigation/        # Navigation setup
│   │   │   ├── screens/           # UI screens
│   │   │   ├── theme/             # Theme configuration
│   │   │   └── viewmodel/         # ViewModels
│   │   └── utils/
│   │       ├── camera/            # Camera utilities
│   │       ├── image/             # Image processing
│   │       ├── localization/      # Language support
│   │       ├── pdf/               # PDF generation
│   │       └── preferences/       # Preferences management
│   └── res/
│       ├── values/                # English resources
│       ├── values-hi/             # Hindi resources
│       ├── values-mr/             # Marathi resources
│       └── xml/                   # XML configurations
├── build.gradle.kts
└── proguard-rules.pro

build.gradle.kts                    # Project-level build
settings.gradle.kts                 # Project settings
```

## Building the Project

### Prerequisites
- Android Studio (Flamingo or newer)
- Android SDK 24 (API 24) or higher
- Gradle 8.0+
- Kotlin 1.9.21+
- Java 17

### Build Steps

1. Clone the repository:
```bash
cd APP
```

2. Sync Gradle (Android Studio will do this automatically)

3. Build the project:
```bash
./gradlew build
```

4. Build and run on emulator/device:
```bash
./gradlew installDebug
```

5. Run specific tests:
```bash
./gradlew test
```

## Running the Application

### First Run
- Grant camera permission when prompted
- Select language (English/Hindi/Marathi)
- Review the tutorial

### Using the App

1. **Analyze Buffalo**:
   - Navigate to "Analyze Buffalo"
   - Capture images from multiple angles
   - Select at least one image to start analysis
   - View detailed results and recommendations

2. **View History**:
   - Navigate to "Analysis History"
   - Browse previous analyses
   - Delete old analyses
   - Search by breed

3. **Generate Report**:
   - Open an analysis result
   - Tap "Generate Report"
   - Share or save the PDF

4. **Settings**:
   - Change language preference
   - Enable/disable dark mode
   - View app information

## Development Notes

### Adding New Models
To add ML models for animal analysis:

1. Create a new analyzer class in `ml/model/`
2. Implement the analysis logic
3. Integrate into `BuffaloAnalysisEngine.kt`
4. Update scoring weights in `ScoringParameters`

### Adding New Features
Follow MVVM pattern:
1. Create data models in `data/model/`
2. Create UI screen in `ui/screens/`
3. Create ViewModel in `ui/viewmodel/`
4. Add navigation route in `ui/navigation/Navigation.kt`
5. Add string resources for multi-language support

### Testing
- Unit tests in `src/test/`
- Instrumented tests in `src/androidTest/`
- Run tests with: `./gradlew test`

## Multi-Language Support

The app supports three languages:
- **English** (en) - Default
- **Hindi** (hi) - हिंदी
- **Marathi** (mr) - मराठी

Language preferences are saved in DataStore and applied at runtime.

## Permissions

The app requires:
- `CAMERA`: For capturing buffalo images
- `READ_EXTERNAL_STORAGE`: For accessing image files
- `WRITE_EXTERNAL_STORAGE`: For saving reports and images

## Performance Optimization

- Image scaling for faster processing (max 1024x1024)
- Lazy loading of images
- Coroutine-based asynchronous operations
- Room database with proper indexing
- ProGuard minification for release builds

## Database

### BuffaloDatabase
- **Entity**: BuffaloAnalysisEntity
- **DAO**: BuffaloAnalysisDao
- **Operations**: Insert, Update, Delete, Query, Paginate

### Queries
- Get all analyses (ordered by date)
- Get analysis by ID
- Get analyses by breed
- Get analyses by date range
- Paginated results

## Error Handling

- Try-catch blocks in critical sections
- Timber logging for debugging
- User-friendly error messages
- Graceful degradation for missing images

## Future Enhancements

- [ ] Cloud backup and sync
- [ ] Farmer dashboard
- [ ] Track milk production after purchase
- [ ] Vaccination reminders
- [ ] Weight estimation from images
- [ ] Heat detection
- [ ] Disease alerts
- [ ] Compare two buffaloes
- [ ] Continuous prediction improvement with user feedback
- [ ] Support for buffalo videos
- [ ] Advanced breed-specific analysis
- [ ] Integration with veterinary records

## Troubleshooting

### Camera not working
- Check camera permissions in system settings
- Ensure camera hardware is available
- Try restarting the app

### Images not saving
- Check storage permissions
- Ensure sufficient storage space
- Check file write permissions

### Analysis errors
- Ensure at least one clear image of the buffalo
- Check if images are properly loaded
- Verify ML models are present

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit changes with clear messages
4. Push to branch
5. Create Pull Request

## License

This project is provided for educational and agricultural purposes.

## Contact & Support

For issues, suggestions, or feedback, please create an issue in the repository.

## Version History

### v1.0.0 (Initial Release)
- Core buffalo analysis functionality
- Multi-language support
- PDF report generation
- Analysis history
- Dark mode support

---

**Note**: This application is designed to assist in dairy buffalo selection based on scientific parameters. While it provides analytical insights, veterinary and experienced dairy experts should be consulted for final purchasing decisions.
