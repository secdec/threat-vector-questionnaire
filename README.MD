![tv-logo](resources/TV-Logo.png?raw=true "tv-logo")

#Summary
The Threat-Vector-Questionnaire is an interactive GUI used to capture the architectural knowledge. This functionality provides the developers with an interactive and easy to use GUI to capture the design knowledge (security patterns as well as technical design decisions) around security architecture of the application. The goal was to develop a threat generation module that can leverage software requirements to generate threats. 

#How it Works
To achieve this goal, we developed a requirements analysis and reasoning module. The Threat-Vector-Questionnaire uses advanced natural language processing techniques (NLP) to identify requirements with security implication. This capability works with the list of requirements stored in a csv file, then automatically identify security related requirements and based on them generates a set of threats relevant to the application.

##Details
Java Spring Web Application
The web application does not support Internet Explorer.

##Installation
[Detailed instructions](https://github.com/secdec/threat-vector-questionnaire/wiki/Enviorment-Installation)


# For Developers & Contributors
## Build Instructions
1. Clone Threat-Vector-Questionnaire -https://github.com/secdec/threat-vector-questionnaire.git
2. (Build from Source Instructions)[https://github.com/secdec/threat-vector-questionnaire/wiki/Building-From-Source]
## License
Licensed under the [Apache-2.0](https://github.com/secdec/threat-vector-questionnaire/blob/master/LICENSE) License.
