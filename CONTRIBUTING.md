# Contributing

---

## Contributor License Agreement (CLA)
By submitting a pull request, you agree that:
- Your contributions will be liecensed irrecoverably under the [GNU General Public License v3.0](https://choosealicense.com/licenses/gpl-3.0/).  
- Your contributions are free from any patents or other intellectual property restrictions that would prevent their use under the license above.

## How to Contribute
1. Clone the repository
    ```shell
    git clone https://github.com/Pugsworth/TankYouVeryMuch.git
    ```
2. Follow the [Fabric Wiki](https://fabricmc.net/wiki/tutorial:setup) to setup your development environment.
3. Create a new branch for your changes. This is just good practice.
4. Make your changes and test them.
5. After testing, build the mod and test it outside your IDE.
6. If everything works, [create a pull request](https://help.github.com/en/articles/creating-a-pull-request) with your changes and enter a good description of what you changed and why.  
See [Pull Requests](#pull-requests) for more information.

If you are unsure if your changes will be accepted, please open an issue first to discuss it.

---

## Guidelines
The primary guidelines for contributing are as follows:
- Use Kotlin as the primary language
- Try to follow atomic commits practices.  
    Don't worry if everything's not exactly perfect, but try to keep commits small.
  - Commit messages should be descriptive enough to know what has happened within the commit.
- For pull requests, please keep each feature into its own pull request.
- If you have any questions or issues, please open an issue and I will respond as soon as I can.

---

## Code Style
First and foremost: Keep your code style consistent  
- Use 4 spaces for indentation.
- Avoid lines over 120 characters long.
- Always use braces for if/else statements.
- Use `val` over `var` whenever possible.
- (Optional) Put braces on the next line for classes, and functions that have a body more than a couple lines.  
    This isn't a hard rule, but it's my preference that makes it easier to read.

I try to remain consistent with my code style, but it tends to wander over time.  
For right now, I'm adhering pretty close to the [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)


## Commit Messages
Short and sweet, but descriptive.

---


## Pull Requests
Only include one feature per pull request.  
The pull request should include a brief description of what the feature is and why it's being added, as well as links to any related issues.

Please don't create a pull request with multiple unrelated features.

#### What's an unrelated feature?  
If you are adding an item called "ExtremeBucket", the changes that are expected are:
- textures
- code
- models
- translation keys  

However, if you also fixed a bug in a different item or improved a texture, that is an unrelated feature.