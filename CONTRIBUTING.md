# Contributing to Parcel Tracking Backend

Thank you for your interest in contributing to **Parcel Tracking Backend**! We welcome contributions from developers of all skill levels.

## Code of Conduct

Please be respectful, constructive, and welcoming to all contributors.

## How Can I Contribute?

### Reporting Bugs
1. Check the [Issues](https://github.com/hieuCM2895/Parcel_Tracking/issues) tab to see if the bug has already been reported.
2. If not, open a new issue using the **Bug Report** template.
3. Include clear steps to reproduce, expected vs. actual behavior, and relevant logs or stack traces.

### Suggesting Enhancements
1. Check existing issues to see if someone already proposed the feature.
2. Open a new issue using the **Feature Request** template.
3. Explain the problem it solves and proposed design/implementation details.

### Submitting a Pull Request
1. Fork the repository and create your branch from `main`:
   ```bash
   git checkout -b feature/my-new-feature
   ```
2. Ensure code compiles and all tests pass:
   ```bash
   ./gradlew test
   ```
3. Commit your changes using descriptive commit messages following Conventional Commits (e.g., `feat:`, `fix:`, `docs:`, `test:`).
4. Push your branch to your fork:
   ```bash
   git push origin feature/my-new-feature
   ```
5. Open a Pull Request against `main`.

## Development Guidelines

- **Java Version**: JDK 17+
- **Framework**: Spring Boot 3.1.4
- **Coding Style**: Follow standard Java naming conventions and Google Java Style Guide.
- **Tests**: Every new feature or bugfix should include corresponding unit or integration tests (MockMvc / JUnit 5).

## License

By contributing to this repository, you agree that your contributions will be licensed under the [MIT License](LICENSE).
