# Laboratory Management System

A Java-based laboratory management system that helps track and manage laboratory equipment, supplies, and their usage.

## Features

- Inventory management for laboratory equipment and supplies
- Price tracking and calculations
- Usage history tracking
- Supply level monitoring
- Interactive command-line interface
- Detailed reporting system

## Prerequisites

- Java JDK 11 or higher
- Maven (for building the project)

## Installation

1. Clone the repository:
```bash
git clone https://github.com/yourusername/lab.git
```

2. Navigate to the project directory:
```bash
cd lab
```

3. Build the project:
```bash
mvn clean install
```

## Usage

Run the application:
```bash
java -jar target/lab.jar
```

### Menu Options

1. View Total Inventory Value
2. View Items Needing Supply
3. View Average Price of Materials
4. View Items Ordered by Price
5. View Most Expensive Item
6. Record Item Usage
7. View Usage History
8. Exit

## Project Structure

```
src/
├── Item.java              # Abstract base class for all laboratory items
├── Laboratory.java        # Main laboratory management class
├── Main.java             # Application entry point
├── Microscope.java       # Microscope equipment class
├── TestTube.java         # Test tube equipment class
├── VolumeFlask.java      # Volume flask equipment class
├── LitmusPaper.java      # Litmus paper supplies class
├── LitmusSolution.java   # Litmus solution supplies class
└── Supplier.java         # Interface for inventory management
```

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- Thanks to all contributors who have helped shape this project
- Special thanks to the laboratory management community for their feedback and suggestions 