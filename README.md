# plc4j-scraper-demo
This is a demo project for the usage of
[PLC4X's](http://plc4x.apache.org/) scraper. PLC4X allows to
collect data from a variety of data sources, mostly
[PLCs](https://en.wikipedia.org/wiki/Programmable_logic_controller).

## Configuration
The target PLCs and scraping jobs are configured in `example.yml`.
The supplied configuration is targeted (IP and fields) at our
laboratory PLC.

## Usage
1. Compile the project
1. Run the program
1. Start the scraper by calling the MBean `ScraperService#start()`
via JMX
1. The scraper reads data from the PLC and periodically prints the
result to `stdout`
1. Stop the scraper by calling the MBean `ScraperService#stop()`
via JMX
1. Stop the program