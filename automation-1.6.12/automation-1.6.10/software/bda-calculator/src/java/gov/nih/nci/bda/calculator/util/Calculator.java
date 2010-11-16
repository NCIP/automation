package gov.nih.nci.bda.calculator.util;

public class Calculator {

	enum Operation {
		add {
			float apply(float operator1, float operator2) {
				return operator1 + operator2;
			}
		},
		sub {
			float apply(float operator1, float operator2) {
				return operator1 - operator2;
			}
		},
		mult {
			float apply(float operator1, float operator2) {
				return operator1 * operator2;
			}
		},
		div {
			float apply(float operator1, float operator2) {
				return operator1 / operator2;
			}
		};

		abstract float apply(float operator1, float operator2);
	}

	public float calculate(String op, float operator1, float operator2) {
		return Operation.valueOf(op).apply(operator1, operator2);
	}

}
